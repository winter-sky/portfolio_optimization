package org.portfolio.optimization.model;

import java.util.Arrays;

/**
 * This class represents financial instrument
 */
public class Instrument {
    private long id;

    private String name;

    private InstrumentType instrumentType;

    /** For actions/obligations - price of one action. For other instruments can be different.*/
    private double minimalLot;

    /** Yield curve.*/
    private double[] yieldCurve;

    /** Risk curve.*/
    private double[] riskCurve;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InstrumentType getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(InstrumentType instrumentType) {
        this.instrumentType = instrumentType;
    }

    /**
     * Gets minimal lot for the instruments, for example price of single action, etc.
     *
     * @return Minimal lot for the instrument.
     */
    public double getMinimalLot() {
        return minimalLot;
    }

    public void setMinimalLot(double minimalLot) {
        this.minimalLot = minimalLot;
    }

    /**
     * Gets yield curve for the instruments.
     *
     * @return Yield curve for the instruments.
     */
    public double[] getYieldCurve() {
        return yieldCurve;
    }

    public void setYieldCurve(double[] yieldCurve) {
        this.yieldCurve = yieldCurve;
    }

    public double[] getRiskCurve() {
        return riskCurve;
    }

    public void setRiskCurve(double[] riskCurve) {
        this.riskCurve = riskCurve;
    }

    @Override
    public String toString() {
        return "Instrument{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", instrumentType=" + instrumentType +
            ", minimalLot=" + minimalLot +
            ", yieldCurve=" + Arrays.toString(yieldCurve) +
            ", riskCurve=" + Arrays.toString(riskCurve) +
            '}';
    }
}
