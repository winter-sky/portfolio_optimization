package org.portfolio.optimization.potfolio;

import org.portfolio.optimization.solution.Risk;

import java.util.List;

public class Portfolio {
    private List<PortfolioInstrument> portfolioInstruments;

    private double totalAmount;

    private double maxAmount;

    private int term;

    private Risk risk;

    public List<PortfolioInstrument> getPortfolioInstruments() {
        return portfolioInstruments;
    }

    public void setPortfolioInstruments(List<PortfolioInstrument> portfolioInstruments) {
        this.portfolioInstruments = portfolioInstruments;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
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

    @Override
    public String toString() {
        return "Portfolio{" +
            "portfolioInstruments=" + portfolioInstruments +
            ", totalAmount=" + totalAmount +
            ", maxAmount=" + maxAmount +
            ", term=" + term +
            ", risk=" + risk +
            '}';
    }
}
