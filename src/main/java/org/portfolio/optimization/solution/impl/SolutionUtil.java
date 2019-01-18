package org.portfolio.optimization.solution.impl;

import org.portfolio.optimization.POException;
import org.portfolio.optimization.potfolio.Portfolio;
import org.portfolio.optimization.potfolio.PortfolioInstrument;
import org.portfolio.optimization.solution.PortfolioTaskType;
import org.portfolio.optimization.solution.Risk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SolutionUtil {
    private static final Logger log = LoggerFactory.getLogger(SolutionUtil.class);

    public static final double[] DFLT_LOSS_SCALE = new double[] {0, 0.05, 0.1, 0.2, 0.25, 0.5, 0.75, 0.9, 0.95, 1.0 };

    private static final double DFLT_SCALE_FACTOR = 100;

    private static final double DFLT_SCALE_FACTOR_PROBABILITY = 1000000;

    public static int getLossIndex(final Risk risk, double[] lossScale) {
        return getLossIndex(risk.getLoss(), lossScale);
    }

    public static int getLossIndex(double loss, double[] lossScale) {
        assert lossScale != null;

        int res = Arrays.stream(lossScale).boxed().filter(d -> d.compareTo(loss) <= 0)
            .collect(Collectors.toList()).size();

        return res > 0 ? res -1 : 0;
    }

    public static String printPortfolio(Portfolio p) {
        StringBuilder sb = new StringBuilder();

        List<PortfolioInstrument> pis = p.getPortfolioInstruments();

        PortfolioTaskType type = p.getType();

        sb.append("\nTerm of investitions: ").append(p.getTerm());
        sb.append("\nMaximum amount: ").append(p.getMaxAmount());
        sb.append("\nType: ").append(type);

        switch (type) {
            case MAXIMIZE_PROFIT: {
                sb.append("\nAcceptable risk: ").append(toPercent(p.getRisk().getLoss())).append("% of loss with probability ")
                    .append(toPercent(p.getRisk().getProbability())).append('%');

                break;
            }
            case MINIMIZE_RISK: {
                sb.append("\nMinimal yield: ").append(round(p.getMinYield() * 100)).append('%');

                break;
            }

            default: {
                assert false;
            }
        }

        sb.append("\n===================================");

        for (PortfolioInstrument pi : pis) {
            sb.append("\nName: ").append(pi.getInstrument().getName())
            .append(", Type: ").append(pi.getInstrument().getInstrumentType())
            .append(", Quantity: ").append(pi.getQuantity())
            .append(", Price: ").append(pi.getInstrument().getMinimalLot())
            .append(", Amount: ").append(pi.getAmount())
            .append(", Yield: ").append(pi.getYield()).append('%')
            .append(", Income: ").append(pi.getIncome())
            .append(", Amount at term: ").append(pi.getAmountAtTerm());
        }

        sb.append("\n===================================");

        sb.append("\nTotal amount: ").append(p.getAmount());
        sb.append("\nTotal income: ").append(p.getIncome());
        sb.append("\nTotal yield: ").append(p.getYield()).append('%');
        sb.append("\nTotal amount at term: ").append(p.getAmountAtTerm());
        sb.append("\nRisk curve: ").append(printRiskCurve(p.getRiskCurve(), p.getLossScale()));

        return sb.toString();
    }

    public static String printRiskCurve(double[] riskCurve, double[] lossScale)  {
        if (riskCurve == null) {
            return null;
        }

        assert lossScale != null;

        assert riskCurve.length == lossScale.length;

        int n = lossScale.length;

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < n; i++) {
            double loss = round(lossScale[i] * 100);
            double prob = roundProb(riskCurve[i] * 100);

            sb.append(prob).append("%(").append(loss).append("%)");

            if (i < n - 1) {
                sb.append(", ");
            }
        }

        return sb.toString();
    }

    private static int toPercent(double val) {
        return (int)(val * 100);
    }

    public static double round(double val) {
        return round(val, DFLT_SCALE_FACTOR);
    }

    public static double roundProb(double val) {
        return round(val, DFLT_SCALE_FACTOR_PROBABILITY);
    }

    public static double round(double val, double scaleFactor) {
        return Math.round(val * scaleFactor) / scaleFactor;
    }

    public static void mix(double[][] deltaProb) {
        int indices[] = new int[deltaProb[0].length];

        List<Double> res = new ArrayList<>();

        while(true) {
            double val = deltaProb[indices[0]][0];

            for (int i = 1; i < indices.length; i++) {
                val = val * deltaProb[indices[i]][i];
            }

            res.add(val);

            System.out.println("value added [val=" + val + ", indices=" + Arrays.toString(indices) + ']');

            boolean incremented = false;

            for (int i = 0; i < indices.length; i++) {
                if (indices[i] < deltaProb.length - 1) {
                    indices[i]++;

                    for (int j = 0; j < i; j++) {
                        indices[j] = 0;
                    }

                    incremented = true;

                    break;
                }
            }

            if (!incremented) {
                break;
            }
        }

        System.out.println("Res: " + res);
    }

    public static double[] mix2(double[][] deltaProb, double[] lossScale) {
        int indices[] = new int[deltaProb[0].length];

        List<Double> res = new ArrayList<>();

        double[] riskCurve = new double[lossScale.length];

        while(true) {
            double val = 1;

            double loss = 0;

            for (int i = 0; i < indices.length; i++) {
                val = val * deltaProb[indices[i]][i];

                loss += lossScale[indices[i]];

                System.out.println("indices[i]=" + indices[i] + ", loss=" + loss);
            }

            loss = loss / indices.length;

            int idx = SolutionUtil.getLossIndex(loss, lossScale);

            riskCurve[idx] += val;

            res.add(val);

            System.out.println("value added [val=" + val + ", loss=" + loss + ", idx=" + idx + ", indices=" + Arrays.toString(indices) + ", risk-curve=" + Arrays.toString(riskCurve) + ']');

            boolean incremented = false;

            for (int i = 0; i < indices.length; i++) {
                if (indices[i] < deltaProb.length - 1) {
                    indices[i]++;

                    for (int j = 0; j < i; j++) {
                        indices[j] = 0;
                    }

                    incremented = true;

                    break;
                }
            }

            if (!incremented) {
                break;
            }
        }

        for (int i = 0; i < riskCurve.length; i++) {
            riskCurve[i] = roundProb(riskCurve[i]);
        }

        System.out.println("Res: " + Arrays.toString(riskCurve));

        return riskCurve;
    }


    public static double[] buildSummaryRiskCurve(double[][] riskCurves, double[] lossScale) {
//        double[][] delta = buildProbabilityDelta(riskCurves);
//
//        print(delta);

        return mix2(riskCurves, lossScale);
    }

    /**
     *
     * @param riskCurves Risk curves for each instrument in the portfolio. Note: columns correspond to each instrument,
     *  each row correspond to loss scale.
     * @param lossScale Loss scale
     * @param weight Weight coefficient for each instrument in the portfolio.
     * @return
     * @throws POException
     */
    public static double[] buildSummaryRiskCurve(double[][] riskCurves, double[] lossScale, double[] weight)
        throws POException {
        if (riskCurves == null) {
            throw new POException("Risk curve is null.");
        }

        if (riskCurves.length == 0) {
            throw new POException("Risk curve is empty.");
        }

        if (lossScale == null) {
            throw new POException("Loss scale is null.");
        }

        if (weight == null) {
            throw new POException("Weight array  is null.");
        }

        if (riskCurves.length != lossScale.length) {
            throw new POException("Arrays size mismatch [risk-curves-length=" + riskCurves.length
                + ", loss-scale-length=" + lossScale.length + ']');
        }

        if (riskCurves[0].length != weight.length) {
            throw new POException("Arrays size mismatch [risk-curves-length=" + riskCurves.length
                + ", weight-length=" + weight.length + ']');
        }

        int indices[] = new int[riskCurves[0].length];

        double[] riskCurve = new double[lossScale.length];

        while(true) {
            double val = 1;

            double loss = 0;

            for (int i = 0; i < indices.length; i++) {
                val = val * riskCurves[indices[i]][i];

                loss += lossScale[indices[i]] * weight[i];

                log.debug("Indices[i]={}, loss={}", indices[i], loss);
            }

            loss = loss / indices.length;

            int idx = SolutionUtil.getLossIndex(loss, lossScale);

            riskCurve[idx] += val;

            log.debug("Value added [val={}, loss={}, idx={}, indices={}, risk-curve={}]", val, loss,  idx,
                Arrays.toString(indices), Arrays.toString(riskCurve));

            boolean incremented = false;

            for (int i = 0; i < indices.length; i++) {
                if (indices[i] < riskCurves.length - 1) {
                    indices[i]++;

                    for (int j = 0; j < i; j++) {
                        indices[j] = 0;
                    }

                    incremented = true;

                    break;
                }
            }

            if (!incremented) {
                break;
            }
        }

        for (int i = 0; i < riskCurve.length; i++) {
            riskCurve[i] = roundProb(riskCurve[i]);
        }

        log.debug("Summary risk curve: " + Arrays.toString(riskCurve));

        return riskCurve;
    }

    public static double[][] buildProbabilityDelta(double[][] riskCurves) {
        int n = riskCurves.length;
        int m = riskCurves[0].length;

        double[][] deltaProb = new double[n][m];

        for (int j = 0; j < m ; j++) {
            for (int i = 0; i < n ; i++) {
                if (i == 0) {
                    deltaProb[n - 1][j] = riskCurves[n - 1][j];
                }
                else {
                    deltaProb[n - 1 - i][j] = roundProb(riskCurves[n - 1 - i][j] - riskCurves[n - i][j]);
                }
            }
        }

        return deltaProb;
    }

    public static void print(double[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(Arrays.toString(arr[i]));
        }
    }
}
