package org.portfolio.optimization.lp.impl;

public class LpSolveConfig {
    /** The value that is used as a tolerance for reduced costs to determine whether a value should be considered as 0.
     *  */
    private Double espd;

    /** The value that is used as a tolerance for the Right Hand Side (RHS) to determine whether a value should be
     * considered as 0.*/
    private Double espb;

    /** The value that is used as a tolerance for rounding values to zero. */
    private Double espel;

    /** Can by any of the following values:

     EPS_TIGHT (0)	Very tight epsilon values (default)
     EPS_MEDIUM (1)	Medium epsilon values
     EPS_LOOSE (2)	Loose epsilon values
     EPS_BAGGY (3)	Very loose epsilon values */
    private Integer esplevel;

    /**
     * @return TBD
     */
    public Double getEspb() {
        return espb;
    }

    /**
     * @param espb
     */
    public void setEspb(Double espb) {
        this.espb = espb;
    }

    /**
     * @return TBD
     */
    public Double getEspd() {
        return espd;
    }

    /**
     * @param espd
     */
    public void setEspd(Double espd) {
        this.espd = espd;
    }

    /**
     * @return TBD
     */
    public Double getEspel() {
        return espel;
    }

    /**
     * @param espel
     */
    public void setEspel(Double espel) {
        this.espel = espel;
    }

    /**
     * @return TBD
     */
    public Integer getEsplevel() {
        return esplevel;
    }

    /**
     * @param esplevel
     */
    public void setEsplevel(Integer esplevel) {
        this.esplevel = esplevel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "LpSolveConfig [espb=" + espb + ", espd=" + espd + ", espel=" + espel + ", esplevel=" + esplevel + ']';
    }
}
