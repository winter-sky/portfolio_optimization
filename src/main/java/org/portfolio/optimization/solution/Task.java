package org.portfolio.optimization.solution;

import org.portfolio.optimization.model.Instrument;

import java.util.Arrays;

/**
 * Represents portfolio optimization task.
 */
public class Task {
    private Instrument[] instrument;

    private TaskType type;

    private double maxAmount;

    private double risk;

    public Instrument[] getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument[] instrument) {
        this.instrument = instrument;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
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
        return "Task{" +
            "instrument=" + Arrays.toString(instrument) +
            ", type=" + type +
            ", maxAmount=" + maxAmount +
            ", risk=" + risk +
            '}';
    }
}
