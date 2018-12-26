package org.portfolio.optimization.solution.impl;

import org.portfolio.optimization.solution.Risk;

import java.util.Arrays;
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
}
