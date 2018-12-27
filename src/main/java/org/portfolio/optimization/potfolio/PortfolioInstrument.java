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
    private double amount;

    /** Yield at requested term. */
    private double yield;

    /** Income of the instrument at the requested term. */
    private double income;

    /** Amount at requested term. */
    private double amountAtTerm;

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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getAmountAtTerm() {
        return amountAtTerm;
    }

    public void setAmountAtTerm(double amountAtTerm) {
        this.amountAtTerm = amountAtTerm;
    }

    /**
     * Gets yield at term, in %.
     *
     * @return Yield at term, in %.
     */
    public double getYield() {
        return yield;
    }

    public void setYield(double yield) {
        this.yield = yield;
    }

    @Override
    public String toString() {
        return "PortfolioInstrument{" +
            "instrument=" + instrument +
            ", quantity=" + quantity +
            ", amount=" + amount +
            ", yield=" + yield +
            ", income=" + income +
            ", amountAtTerm=" + amountAtTerm +
            '}';
    }
}
