package org.portfolio.optimization.lp.impl;

import org.portfolio.optimization.lp.LpProblemConstraint;
import org.portfolio.optimization.lp.LpProblemSolver;

/**
 * TBD: add comments for LpProblemSolverAdaptor.java.
 */
public abstract class LpProblemSolverAdaptor implements LpProblemSolver {
    /**
     * TBD
     *
     * @param constraint
     */
    protected void printConstraint(LpProblemConstraint constraint) {// You can replace it with the default method
        // in the LpProblemSolver interface
        LpUtils.printConstraint(constraint.getName(), constraint.getCoeffs(), constraint.getRelation(),
                constraint.getValue());
    }
}
