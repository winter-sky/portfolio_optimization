package org.portfolio.optimization.solution;

import org.portfolio.optimization.model.Instrument;

import java.util.Arrays;

/**
 * Represents portfolio optimization task.
 */
public class PortfolioTask {
    private Instrument[] instrument;

    private PortfolioTaskType type;

    private double maxAmount;

    private double risk;

    public Instrument[] getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument[] instrument) {
        this.instrument = instrument;
    }

    public PortfolioTaskType getType() {
        return type;
    }

    public void setType(PortfolioTaskType type) {
        this.type = type;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public double getRisk() {
        return risk;
    }

    public void setRisk(double risk) {
        this.risk = risk;
    }

    @Override
    public String toString() {
        return "PortfolioTask{" +
            "instrument=" + Arrays.toString(instrument) +
            ", type=" + type +
            ", maxAmount=" + maxAmount +
            ", risk=" + risk +
            '}';
    }
}
