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
     * @throws LpException
     */
    public void addConstraint(LpProblemConstraint constraint) throws LpException;

    /**
     * 
     * TBD
     * 
     * @param coeff
     * @param dir
     * @throws LpException
     */
    public void addObjective(double[] coeff, TargetDirection dir) throws LpException;

    /**
     * 
     * TBD
     * 
     * @return TBD
     * @throws LpException
     */
    public LpProblemResult solve() throws LpException, POException;
}
