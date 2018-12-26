package org.portfolio.optimization.solution;

import org.portfolio.optimization.model.Instrument;

import java.util.Arrays;

/**
 * Represents portfolio optimization task.
 */
public class PortfolioTask {
    private Instrument[] instrument;

    private PortfolioTaskType type;

    private double[] lossScale;

    /** Maximum amount of money user wishes to invest into portfolio. */
    private double maxAmount;

    private Risk risk;

    private int term;

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

    public Risk getRisk() {
        return risk;
    }

    public void setRisk(Risk risk) {
        this.risk = risk;
    }

    /**
     * Gets desired term of investments.
     *
     * @return Term of investment
     */
    public int getTerm() {
        return term;
    }

    /**
     * Sets desired term of investments.
     *
     * @param term Desired term of investments.
     */
    public void setTerm(int term) {
        this.term = term;
    }

    public double[] getLossScale() {
        return lossScale;
    }

    public void setLossScale(double[] lossScale) {
        this.lossScale = lossScale;
    }

    @Override
    public String toString() {
        return "PortfolioTask{" +
            "instrument=" + Arrays.toString(instrument) +
            ", type=" + type +
            ", lossScale=" + Arrays.toString(lossScale) +
            ", maxAmount=" + maxAmount +
            ", risk=" + risk +
            ", term=" + term +
            '}';
    }
}
