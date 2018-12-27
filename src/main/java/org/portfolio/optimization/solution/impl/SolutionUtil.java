package org.portfolio.optimization.solution.impl;

import org.portfolio.optimization.potfolio.Portfolio;
import org.portfolio.optimization.potfolio.PortfolioInstrument;
import org.portfolio.optimization.solution.Risk;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SolutionUtil {
    public static final double[] DFLT_LOSS_SCALE = new double[] {0, 0.05, 0.1, 0.2, 0.25, 0.5, 0.75, 0.9, 0.95, 1.0 };

    public static int getLossIndex(final Risk risk, double[] lossScale) {
        return getLossIndex(risk.getLoss(), lossScale);
    }

    public static int getLossIndex(double loss, double[] lossScale) {
        assert lossScale != null;

        int idx =  Arrays.stream(lossScale).boxed().filter(d -> d.compareTo(loss) <= 0)
            .collect(Collectors.toList()).size();

        return idx > 0 ? idx - 1 : 0;
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
            .append(", Amount: ").append(pi.getTotalAmount());
        }

        sb.append("\n===================================");

        sb.append("\nTotal amount: ").append(p.getTotalAmount());

        return sb.toString();
    }

    private static int toPercent(double val) {
        return (int)(val * 100);
    }
}
