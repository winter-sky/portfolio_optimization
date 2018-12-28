package org.portfolio.optimization;

import org.junit.Test;
import org.portfolio.optimization.solution.impl.SolutionUtil;

import javax.sound.midi.Soundbank;

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
}
