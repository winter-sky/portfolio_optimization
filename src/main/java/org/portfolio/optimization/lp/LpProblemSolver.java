package org.portfolio.optimization.lp;

import org.portfolio.optimization.POException;

/**
 * TBD: add comments for LpProblemSolver.java.
 */

public interface LpProblemSolver {
    /**
     * 
     * TBD
     * 
     * @param constraint
     * @throws POException
     */
    public void addConstraint(LpProblemConstraint constraint) throws POException;

    /**
     * 
     * TBD
     * 
     * @param coeff
     * @param dir
     * @throws POException
     */
    public void addObjective(double[] coeff, TargetDirection dir) throws POException;

    /**
     * 
     * TBD
     * 
     * @return TBD
     * @throws POException
     */
    public LpProblemResult solve() throws POException;
}
