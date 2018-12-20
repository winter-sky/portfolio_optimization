package org.portfolio.optimization.potfolio;

import org.portfolio.optimization.model.Instrument;

public class PortfolioInstrument {
    private Instrument instrument;

    private int quantity;

    private double totalAmount;

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "PortfolioInstrument{" +
            "instrument=" + instrument +
            ", quantity=" + quantity +
            ", totalAmount=" + totalAmount +
            '}';
    }
}
