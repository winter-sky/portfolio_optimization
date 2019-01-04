package org.portfolio.optimization.solution.impl;

import org.portfolio.optimization.POException;
import org.portfolio.optimization.lp.*;
import org.portfolio.optimization.lp.impl.LpSolveLpProblemSolver;
import org.portfolio.optimization.model.Instrument;
import org.portfolio.optimization.potfolio.Portfolio;
import org.portfolio.optimization.potfolio.PortfolioInstrument;
import org.portfolio.optimization.solution.PortfolioFinder;
import org.portfolio.optimization.solution.PortfolioTask;
import org.portfolio.optimization.solution.PortfolioTaskType;
import org.portfolio.optimization.solution.Risk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PortfolioFinderImpl implements PortfolioFinder {
    private static final Logger log = LoggerFactory.getLogger(PortfolioFinderImpl.class);

    @Override
    public Portfolio find(PortfolioTask task) throws POException {
        log.info("Building porfolio process started [task={}]", task);

        validateTask(task);

        Instrument[] instrs = task.getInstrument();

        int n = instrs.length;

        int size = 2 * n + 2;

        // Yield in the requested term.
        double[] yield = new double[n];

        int term = task.getTerm();

        for (int i = 0; i < n; i++) {
            yield[i] = instrs[i].getYieldCurve()[term];
        }

        double[] riskArr = new double[n];

        int lossIdx = SolutionUtil.getLossIndex(task.getRisk(), task.getLossScale());

        for (int i = 0; i < n; i++) {
            riskArr[i] = instrs[i].getRiskCurve()[lossIdx];
        }

        LpProblemSolver solver = new LpSolveLpProblemSolver(size, 0);

        switch (task.getType()) {
            case MAXIMIZE_PROFIT: {
                addMaxAmountConstaint(solver, size, instrs, task.getMaxAmount());
                addAuxConstraint(solver, size, instrs);
                addRiskContraint(solver, size, instrs, riskArr, task.getRisk(), n, task.getLossScale());

                addTargetFunction(solver, size, instrs, yield);

                break;
            }
            case MINIMIZE_RISK: {
                addMinYieldConstraint(solver, instrs, size, yield, task.getMinYield());

                break;
            }

            default: {
                // No other types supported at the moment.
                assert false;
            }
        }

        LpProblemResult res = solver.solve();

        log.info("Linear programming solution found [result={}]", res);

        return processResult(res, instrs, task);
    }

    private void addMinYieldConstraint(LpProblemSolver solver, Instrument[] instrs, int size, double[] yield,
        Double minYield) throws POException {
        // Controlled by validation.
        assert minYield != null;

        // Sum[I in N] (yc[i][t] - yd) * x[i]*p[i] >= 0
        double[] coeff = new double[size];

        int n = instrs.length;

        for (int i = 0; i < n; i++) {
            coeff[i] = (yield[i] - minYield) * instrs[i].getMinimalLot();
        }

        solver.addConstraint(new LpProblemConstraint(coeff, Relation.GE, 0));
    }

    private void addTargetFunction(LpProblemSolver solver, int size, Instrument[] instrs, double[] yield) throws POException {
        // Target function.
        //f (X) =  (-1)*Sum[I in N] x[i]*p[i]*yc[i][t]
        int n = instrs.length;

        double[] coeff = new double[size];

        for (int i = 0; i < size; i++) {
            if (i < n) {
                coeff[i] = yield[i] * instrs[i].getMinimalLot();
            }
        }

        solver.addObjective(coeff, TargetDirection.MAXIMUM);
    }

    private void addAuxConstraint(LpProblemSolver solver, int size, Instrument[] instrs) throws POException {
        double[] coeff;

        int n = instrs.length;

        for (int i = 0; i < n; i++) {
            coeff = new double[size];

            // Z[i] = p[i] – y, for all i {0, N}, Z[i] > 0;
            coeff[n + i] = 1;
            coeff[2 * n] = 1;

            solver.addConstraint(new LpProblemConstraint(coeff, Relation.EQ, instrs[i].getMinimalLot()));
        }
    }

    private void addMaxAmountConstaint(LpProblemSolver solver, int size, Instrument[] instrs, double maxAmount) throws POException {
        // SUM[i in N] p[i]*x[i] + y = su
        double[] coeff = new double[size];

        int n = instrs.length;

        for (int i = 0; i < size; i++) {
            if (i < n) {
                coeff[i] = instrs[i].getMinimalLot();
            }
        }

        coeff[2 * n] = 1;

        solver.addConstraint(new LpProblemConstraint(coeff, Relation.EQ, maxAmount));
    }

    private void addRiskContraint(LpProblemSolver solver, int size, Instrument[] instrs, double[] riskArr, Risk risk,
        int n, double[] lossScale) throws POException {
        // Risk constraint.
        double[] coeff = new double[size];

        int idx = SolutionUtil.getLossIndex(risk, lossScale);

        for (int i = 0; i < n; i++) {
            double riskProb = instrs[i].getRiskCurve()[idx];

            coeff[i] = (risk.getProbability() - riskProb) * instrs[i].getMinimalLot();

            log.debug("Coefficient added [instr=" + instrs[i].getName() + ", index=" + idx + ", risk-prob=" + riskProb
                + ", coeff=" + coeff[i] + ']');
        }

        solver.addConstraint(new LpProblemConstraint(coeff, Relation.GE, 0));
    }

    private Portfolio processResult(LpProblemResult res, Instrument[] instrs, PortfolioTask task) {
        double[] arr = res.getSolution();

        List<PortfolioInstrument> lst = new ArrayList<>();

        double totalAmount = 0;
        double totalIncome = 0;
        double totalAmountAtTerm = 0;

        for (int i = 0; i < instrs.length; i++) {
            if (Double.compare(arr[i], 0) > 0) {
                PortfolioInstrument pi = new PortfolioInstrument();

                pi.setInstrument(instrs[i]);
                pi.setQuantity((int)arr[i]);
                pi.setAmount(instrs[i].getMinimalLot() * pi.getQuantity());
                pi.setYield(SolutionUtil.round(instrs[i].getYieldCurve()[task.getTerm()] * 100));
                pi.setIncome(SolutionUtil.round(pi.getAmount() * instrs[i].getYieldCurve()[task.getTerm()]));
                pi.setAmountAtTerm(pi.getAmount() + pi.getIncome());

                totalAmount += pi.getAmount();
                totalIncome += pi.getIncome();
                totalAmountAtTerm += pi.getAmountAtTerm();

                lst.add(pi);
            }
        }

        double[] riskCurve = buildRiskCurve(lst, totalAmount, task.getLossScale());

        Portfolio p = new Portfolio();

        p.setMaxAmount(task.getMaxAmount());
        p.setAmount(totalAmount);
        p.setAmountAtTerm(SolutionUtil.round(totalAmountAtTerm));
        p.setTerm(task.getTerm());
        p.setRisk(task.getRisk());
        p.setPortfolioInstruments(lst);
        p.setIncome(SolutionUtil.round(totalIncome));
        p.setYield(SolutionUtil.round(totalIncome * 100 / totalAmount));
        p.setRiskCurve(riskCurve);
        p.setLossScale(task.getLossScale());

        return p;
    }

    /**
     * Builds risk curve for entire portfolio.
     *
     * @return TBD
     */
    private double[] buildRiskCurve(List<PortfolioInstrument> pis, double totalAmount, double[] lossScale) {
        if (pis.isEmpty()) {
            return null;
        }

        double[] riskCurve = pis.get(0).getInstrument().getRiskCurve();

        int size = pis.size();

        double[] weights = new double[size];

        for (int i = 0; i < size; i++) {
            PortfolioInstrument pi = pis.get(i);

            weights[i] = SolutionUtil.roundProb(pi.getAmount() / totalAmount);
        }

        double[][] riskCurves = new double[lossScale.length][];

        for (int i = 0; i < riskCurve.length; i++) {
            riskCurves[i] = new double[size];

            for (int j = 0; j < size; j++) {
                riskCurves[i][j] = pis.get(j).getInstrument().getRiskCurve()[i];
            }
        }

        try {
            return SolutionUtil.buildSummaryRiskCurve(riskCurves, lossScale, weights);
        }
        catch (POException e) {
            log.error("Cannot calculate risk curve for portfolio.", e);

            return null;
        }
    }

    private void validateTask(PortfolioTask task) throws POException {
        Instrument[] insts = task.getInstrument();

        if (insts == null) {
            throw new POException("Instruments are not defined [task=" + task + ']');
        }

        if (insts.length == 0) {
            throw new POException("Instruments are empty [task=" + task + ']');
        }

        int term = task.getTerm();

        if (term < 0) {
            throw new POException("Invalid term [term=" + term + ']');
        }

        PortfolioTaskType type = task.getType();

        switch (type) {
            case MAXIMIZE_PROFIT: {
                if (task.getRisk() == null) {
                    throw new POException("Risk must be defined for the given task type [type=" + type
                        + ", task=" + task + ']');
                }

                break;
            }

            case MINIMIZE_RISK: {
                if (task.getMinYield() == null) {
                    throw new POException("Minimal yield must be defined for the given task type [type=" + type
                        + ", task=" + task + ']');
                }

                if (task.getMinYield() < 0) {
                    throw new POException("Minimal yield must be non-negative [min-yield=" + task.getMinYield() +
                        ", task=" + task + ']');
                }

                break;
            }

            default: {
                // No other types defined.
                assert false;
            }
        }

        int yeildCurveSize = -1;
        int riskCurveSize = -1;

        for (Instrument inst : insts) {
            yeildCurveSize = checkArray(inst.getYieldCurve(), yeildCurveSize, term, "Yeild curve", inst);
            riskCurveSize = checkArray(inst.getRiskCurve(), riskCurveSize, term, "Risk curve", inst);

            validateRiskCurve(inst.getRiskCurve());
        }

        if (task.getMaxAmount() <= 0) {
            throw new POException("Invalid max amount [max-amount=" + task.getMaxAmount() + ']');
        }
    }

    private int checkArray(double[] arr, int size, int term, String name, Instrument instr) throws POException {
        if (arr == null) {
            throw  new POException(name + " is null [instrument=" + instr + ']');
        }

        if (arr.length == 0) {
            throw  new POException(name + " is empty [instrument=" + instr + ']');
        }

        if (arr.length < term) {
            throw  new POException(name + " size is less than requested term [size=" + arr.length + ", instrument=" + instr + ']');
        }

        if (size != -1) {
            if (size != arr.length) {
                throw  new POException(name + " has unexpected size [expected=" + size + ", actual=" + arr.length
                    + ", instrument=" + instr + ']');
            }
        }

        return arr.length;
    }

    private void validateRiskCurve(double[] riskCurve) throws POException {
        double sum = SolutionUtil.roundProb(Arrays.stream(riskCurve).sum());

        if (Double.compare(1., sum) != 0) {
            throw new POException("Invalid risk curve. Sum of all probablilites is not 1 [sum=" + sum + ", risk-curve="
                + Arrays.toString(riskCurve) + ']');
        }
    }
}
