package org.portfolio.optimization.solution.impl;

import org.portfolio.optimization.POException;
import org.portfolio.optimization.lp.LpConfig;
import org.portfolio.optimization.lp.LpProblemSolver;
import org.portfolio.optimization.lp.impl.LpSolveLpProblemSolver;
import org.portfolio.optimization.model.Instrument;
import org.portfolio.optimization.potfolio.Portfolio;
import org.portfolio.optimization.solution.PortfolioFinder;
import org.portfolio.optimization.solution.PortfolioTask;

public class PortfolioFinderImpl implements PortfolioFinder {
    @Override
    public Portfolio find(PortfolioTask task) throws POException {
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

        LpProblemSolver solver = new LpSolveLpProblemSolver(size, 0);

        return null;
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

        int yeildCurveSize = -1;
        int riskCurveSize = -1;

        for (Instrument inst : insts) {
            yeildCurveSize = checkArray(inst.getYieldCurve(), yeildCurveSize, term, "Yeild curve", inst);
            riskCurveSize = checkArray(inst.getRiskCurve(), riskCurveSize, term, "Risk curve", inst);
        }

        if (task.getMaxAmount() <= 0) {
            throw new POException("Invalid max amount [max-amount=" + task.getMaxAmount() + ']');
        }

        if (task.getTerm() <= 0) {
            throw new POException("Invalid term [term=" + task.getTerm() + ']');
        }

        if (task.getRisk() == null) {
            throw new POException("Risk is null [task=" + task + ']');
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
            throw  new POException(name + " size is less than requested term [size=" + arr.length + "minstrument=" + instr + ']');
        }

        if (size != -1) {
            if (size != arr.length) {
                throw  new POException(name + " has unexpected size [expected=" + size + ", actual=" + arr.length
                    + ", instrument=" + instr + ']');
            }
        }

        return arr.length;
    }
}
