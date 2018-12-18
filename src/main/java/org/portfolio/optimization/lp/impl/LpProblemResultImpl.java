package org.portfolio.optimization.lp.impl;

import org.portfolio.optimization.lp.LpProblemResult;
import org.portfolio.optimization.lp.LpStatus;

import java.util.Arrays;

/**
 * TBD: add comments for LpProblemResultImpl.java.
 */
public class LpProblemResultImpl implements LpProblemResult {
    /** */
    private final LpStatus status;

    /** */
    private final double objective;

    /** */
    private final double[] solution;

    /**
     * TBD
     * 
     * @param status
     * @param objective
     * @param solution
     */
    public LpProblemResultImpl(LpStatus status, double objective, double[] solution) {
        this.status = status;
        this.objective = objective;
        this.solution = solution;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LpStatus getStatus() {
        return status;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double[] getSolution() {
        return solution;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getObjective() {
        return objective;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "LpProblemResultImpl [status=" + status + ", objective=" + objective + ", solution="
            + Arrays.toString(solution) + "]";
    }
}
