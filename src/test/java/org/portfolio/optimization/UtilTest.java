package org.portfolio.optimization;

import org.junit.Test;
import org.portfolio.optimization.solution.impl.SolutionUtil;

import javax.sound.midi.Soundbank;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class UtilTest {
    @Test
    public void testLossScale() throws Exception {
        assertEquals(4, SolutionUtil.getLossIndex(0.25, SolutionUtil.DFLT_LOSS_SCALE));
        assertEquals(3, SolutionUtil.getLossIndex(0.24, SolutionUtil.DFLT_LOSS_SCALE));
        assertEquals(3, SolutionUtil.getLossIndex(0.24999, SolutionUtil.DFLT_LOSS_SCALE));
        assertEquals(3, SolutionUtil.getLossIndex(0.2000, SolutionUtil.DFLT_LOSS_SCALE));
        assertEquals(2, SolutionUtil.getLossIndex(0.1999, SolutionUtil.DFLT_LOSS_SCALE));
    }

    @Test
    public void testBuildProbabliltyDelta() throws Exception {
        double[][] arr = new double[][] {{5, 7},{3, 4}, {2, 1}};

        double[][] expected  = new double[][] {{2, 3},{1, 3}, {2, 1}};

        double[][] res = SolutionUtil.buildProbabilityDelta(arr);

        TestUtils.print(res);

        TestUtils.assertArraysEquals(expected, res);
    }

    @Test
    public void testSummaryRiskCurve() throws Exception {
        double[][] riskCurves = new double[][] {{0.5, 0.71},{0.3, 0.2}, {0.2, 0.1}};

        TestUtils.print(riskCurves);

        double[] lossScale = {0, 0.5, 1};

        double[] res = SolutionUtil.buildSummaryRiskCurve(riskCurves, lossScale);

        System.out.println("Res: " + Arrays.toString(res));
    }

    @Test
    public void testSummaryRiskCurve2() throws Exception {
        double[][] riskCurves = new double[][] {{0.87, 0.43},{0.05, 0.35}, {0.04, 0.11}, {0.03, 0.08}, {0.01, 0.03}};

        TestUtils.print(riskCurves);

        for (int j = 0; j < riskCurves[0].length; j++) {
            double sum = 0;

            for (int i = 0; i < riskCurves.length; i++) {
                sum += riskCurves[i][j];
            }

            System.out.println("Sum: " + sum);
        }

        double[] lossScale = {0, 0.25, 0.5, 0.75, 1};

        double[] res = SolutionUtil.buildSummaryRiskCurve(riskCurves, lossScale);

        System.out.println("Res: " + Arrays.toString(res));

        double sum = 0;

        for (int i = 0; i < res.length; i++) {
            sum += res[i];
        }

        System.out.println("Sum: " + SolutionUtil.roundProb(sum));
    }
}
