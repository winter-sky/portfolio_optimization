package org.portfolio.optimization;

import org.junit.Test;
import org.portfolio.optimization.model.Instrument;
import org.portfolio.optimization.solution.impl.SolutionUtil;

public class ProbablityTest {
    @Test
    public void testProbability() {
        Instrument[] instrs = TestUtils.TEST_INSTRUMENTS;

        double[][] deltaProb = buildProbabilityDelta(instrs);

        TestUtils.print(deltaProb);

        SolutionUtil.mix2(deltaProb, SolutionUtil.DFLT_LOSS_SCALE);
    }

    private double[][] buildProbabilityDelta(Instrument[] instrs) {
        double[][] deltaProb = new double[instrs[0].getRiskCurve().length][instrs.length];

        for (int i = 0; i < instrs[0].getRiskCurve().length; i++) {
            deltaProb[i] = new double[instrs.length];
        }

        for (int i = 0; i < instrs.length ; i++) {
            int m = instrs[i].getRiskCurve().length;

            for (int j = 0; j < instrs[i].getRiskCurve().length ; j++) {
                if (j == 0) {
                    deltaProb[m - 1][i] =  instrs[i].getRiskCurve()[m - 1];
                }
                else {
                    deltaProb[m - 1 - j][i] = instrs[i].getRiskCurve()[m - 1 - j] - instrs[i].getRiskCurve()[m - j];
                }
            }
        }

        return deltaProb;
    }

    @Test
    public void testMix() throws Exception {
        double[][] arr = {{1., 2., 3.},{4., 5., 6.},{7., 8., 9.}};

        TestUtils.print(arr);

        SolutionUtil.mix(arr);
    }

    @Test
    public void testMix2() throws Exception {
        double[][] arr = {{1., 2., 3.},{4., 5., 6.}};

        TestUtils.print(arr);

        SolutionUtil.mix(arr);
    }

    @Test
    public void testMix3() throws Exception {
        double[][] arr = {{1., 2.},{4., 5.},{7., 8.}};

        TestUtils.print(arr);

        SolutionUtil.mix(arr);
    }

}
