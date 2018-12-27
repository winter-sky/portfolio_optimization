package org.portfolio.optimization.potfolio;

import org.portfolio.optimization.solution.Risk;

import java.util.List;

public class Portfolio {
    private List<PortfolioInstrument> portfolioInstruments;

    private double maxAmount;

    private double amount;

    private double amountAtTerm;

    private int term;

    private Risk risk;

    private double yield;

    private double income;

    public List<PortfolioInstrument> getPortfolioInstruments() {
        return portfolioInstruments;
    }

    public void setPortfolioInstruments(List<PortfolioInstrument> portfolioInstruments) {
        this.portfolioInstruments = portfolioInstruments;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public Risk getRisk() {
        return risk;
    }

    public void setRisk(Risk risk) {
        this.risk = risk;
    }

    public double getYield() {
        return yield;
    }

    public void setYield(double yield) {
        this.yield = yield;
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

    @Override
    public String toString() {
        return "Portfolio{" +
            "portfolioInstruments=" + portfolioInstruments +
            ", amount=" + amount +
            ", maxAmount=" + maxAmount +
            ", term=" + term +
            ", risk=" + risk +
            ", yield=" + yield +
            ", income=" + income +
            ", amountAtTerm=" + amountAtTerm +
            '}';
    }
}
