package org.portfolio.optimization.potfolio;

import org.portfolio.optimization.model.Instrument;

/**
 * Financial instrument as a part of portfolio. Includes instrument itself, quantity (f.e. number of actions, etc),
 * and total amount of money spend on the particular instrument.
 */
public class PortfolioInstrument {
    private Instrument instrument;

    /** Quantity (i.e. number of action of the particular instruments in the portfolio, etc).*/
    private int quantity;

    /** Total amount of money spent on the particular instrument in the portfolio.*/
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
