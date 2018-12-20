package org.portfolio.optimization.potfolio;

import java.util.List;

public class Portfolio {
    private List<PortfolioInstrument> portfolioInstruments;

    private double totalAmount;

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

    @Override
    public String toString() {
        return "Portfolio{" +
            "portfolioInstruments=" + portfolioInstruments +
            ", totalAmount=" + totalAmount +
            '}';
    }
}
