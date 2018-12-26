package org.portfolio.optimization;

import org.junit.Test;
import org.portfolio.optimization.solution.impl.SolutionUtil;

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
}
