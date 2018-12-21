package org.portfolio.optimization.solution;

public class Risk {
    private double loss;

    private double probability;

    public double getLoss() {
        return loss;
    }

    /**
     * Set loss limit.
     *
      * @param loss Number from 0 to 1 defining potential loss. 0 means no loss, 1 means loss of all investments.
     *  For example, 0.1 means 10% of loss.
     */
    public void setLoss(double loss) {
        this.loss = loss;
    }

    /**
     * Gets probability of given loss.
     *
     * @return Probability, from 0 to 1.
     */
    public double getProbability() {
        return probability;
    }

    /**
     * Sets probability of given loss.
     *
     * @param probability Probability, from 0 to 1.
     */
    public void setProbability(double probability) {
        this.probability = probability;
    }

    @Override
    public String toString() {
        return "Risk{" +
            "loss=" + loss +
            ", probability=" + probability +
            '}';
    }
}
