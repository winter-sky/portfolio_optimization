package org.portfolio.optimization.potfolio;

import org.portfolio.optimization.solution.Risk;

import java.util.Arrays;
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

    private double[] riskCurve;

    private double[] lossScale;

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

    public double[] getRiskCurve() {
        return riskCurve;
    }

    public void setRiskCurve(double[] riskCurve) {
        this.riskCurve = riskCurve;
    }

    public double[] getLossScale() {
        return lossScale;
    }

    public void setLossScale(double[] lossScale) {
        this.lossScale = lossScale;
    }

    @Override
    public String toString() {
        return "Portfolio{" +
            "portfolioInstruments=" + portfolioInstruments +
            ", maxAmount=" + maxAmount +
            ", amount=" + amount +
            ", amountAtTerm=" + amountAtTerm +
            ", term=" + term +
            ", risk=" + risk +
            ", yield=" + yield +
            ", income=" + income +
            ", riskCurve=" + Arrays.toString(riskCurve) +
            '}';
    }
}
