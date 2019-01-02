package org.portfolio.optimization.lp.impl;

import org.portfolio.optimization.lp.LpProblemResult;
import org.portfolio.optimization.lp.LpStatus;

import java.util.Arrays;

/**
 * Represents a linear programming problem solution.
 */
public class LpProblemResultImpl implements LpProblemResult {
    /** */
    private final LpStatus status;

    /** Value of the target function */
    private final double objective;

    /** The array of values ​​of the desired variables */
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
