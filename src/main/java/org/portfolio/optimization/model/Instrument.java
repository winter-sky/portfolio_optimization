package org.portfolio.optimization.model;

import java.util.Arrays;

/**
 * This class represents financial instrument
 */
public class Instrument {
    private long id;

    private String name;

    private InstrumentType instrumentType;

    private double minimalLot;

    private double[] yieldCurve;

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

    public double getMinimalLot() {
        return minimalLot;
    }

    public void setMinimalLot(double minimalLot) {
        this.minimalLot = minimalLot;
    }

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
