package org.portfolio.optimization.lp.impl;

public class LpSolveConfig {
    /** */
    private Double espd;

    /** */
    private Double espb;

    /** */
    private Double espel;

    /** */
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
