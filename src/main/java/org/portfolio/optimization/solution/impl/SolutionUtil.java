package org.portfolio.optimization.solution.impl;

import org.portfolio.optimization.potfolio.Portfolio;
import org.portfolio.optimization.potfolio.PortfolioInstrument;
import org.portfolio.optimization.solution.Risk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SolutionUtil {
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

        sb.append("\nTerm of investitions: ").append(p.getTerm());
        sb.append("\nMaximum amount: ").append(p.getMaxAmount());
        sb.append("\nAcceptable risk: ").append(100 - toPercent(p.getRisk().getLoss())).append("% of loss with probability ")
            .append(toPercent(p.getRisk().getProbability())).append('%');

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

    private static double round(double val, double scaleFactor) {
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

    public static void mix2(double[][] deltaProb, double[] lossScale) {
        int indices[] = new int[deltaProb[0].length];

        List<Double> res = new ArrayList<>();

        double[] riskCurve = new double[lossScale.length];

        while(true) {
            double val = deltaProb[indices[0]][0];

            double loss = 0;

            for (int i = 1; i < indices.length; i++) {
                val = val * deltaProb[indices[i]][i];

                loss += lossScale[indices[i]];
            }

            int idx = SolutionUtil.getLossIndex(loss, lossScale);

            riskCurve[idx] += val;

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

        for (int i = 0; i < riskCurve.length; i++) {
            riskCurve[i] = roundProb(riskCurve[i]);
        }

        System.out.println("Res: " + Arrays.toString(riskCurve));
    }
}
