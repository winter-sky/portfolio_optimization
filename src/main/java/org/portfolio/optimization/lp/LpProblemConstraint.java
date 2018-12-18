package org.portfolio.optimization.lp;

import java.util.Arrays;

/**
 * TBD: add comments for LpProblemConstraint.java.
 */
public class LpProblemConstraint {
    /** */
    private final double[] coeffs;

    /** */
    private final Relation relation;

    /** */
    private final double value;

    /** */
    private final String name;

    /**
     * TBD
     * 
     * @param coeffs
     * @param relation
     * @param value
     */
    public LpProblemConstraint(double[] coeffs, Relation relation, double value) {
        this(coeffs, relation, value, null);
    }

    /**
     * 
     * TBD
     * 
     * @param coeffs
     * @param relation
     * @param value
     * @param name
     */
    public LpProblemConstraint(double[] coeffs, Relation relation, double value, String name) {
        this.coeffs = coeffs;
        this.value = value;
        this.relation = relation;
        this.name = name;
    }

    /**
     * @return TBD
     */
    public double[] getCoeffs() {
        return coeffs;
    }

    /**
     * @return TBD
     */
    public Relation getRelation() {
        return relation;
    }

    /**
     * @return TBD
     */
    public double getValue() {
        return value;
    }

    /**
     * @return TBD
     */
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "LpProblemConstraint [coeffs=" + Arrays.toString(coeffs) + ", relation=" + relation + ", value=" + value
            + ", name=" + name + "]";
    }
}
