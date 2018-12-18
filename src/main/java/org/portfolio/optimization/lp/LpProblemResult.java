package org.portfolio.optimization.lp;

/**
 * TBD: add comments for LpProblemResult.java.
 */
public interface LpProblemResult {
    /**
     * 
     * TBD
     * 
     * @return TBD
     */
    public LpStatus getStatus();

    /**
     * 
     * TBD
     * 
     * @return TBD
     */
    public double[] getSolution();

    /**
     * 
     * TBD
     * 
     * @return TBD
     */
    public double getObjective();
}
