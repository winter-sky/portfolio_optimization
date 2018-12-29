package org.portfolio.optimization;

import org.junit.Test;
import org.portfolio.optimization.solution.PortfolioFinder;
import org.portfolio.optimization.solution.PortfolioTask;
import org.portfolio.optimization.solution.PortfolioTaskType;
import org.portfolio.optimization.solution.Risk;
import org.portfolio.optimization.solution.impl.PortfolioFinderImpl;
import org.portfolio.optimization.solution.impl.SolutionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PorfolioTest {
    private static final Logger log = LoggerFactory.getLogger(PorfolioTest.class);

    @Test
    public void testPortfolio1() throws Exception {
        testPortfolio(20000, 4, 0.05, 0.05);
    }

    /**
     * In this example, low risk is required, so only Obligations should be included
     *
     * @throws Exception
     */
    @Test
    public void testPortfolio2() throws Exception {
        testPortfolio(100000, 2, 0.999, 0.05);
    }

    /**
     * In this example, high risk is accepted
     *
     * @throws Exception
     */
    @Test
    public void testPortfolio3() throws Exception {
        testPortfolio(15000, 5, 0.5, 0.05);
    }

    /**
     * In this example, high risk is accepted
     *
     * @throws Exception
     */
    @Test
    public void testPortfolio4() throws Exception {
        testPortfolio(100000, 4, 0.1, 0.01);
        testPortfolio(100000, 4, 0.1, 0.05);
        testPortfolio(100000, 4, 0.1, 0.5);
    }

    /**
     * In this example, high risk is accepted
     *
     * @throws Exception
     */
    @Test
    public void testPortfolio5() throws Exception {
        testPortfolio(100000, 4, 0.5, 0.05);
        testPortfolio(100000, 4, 0.1, 0.05);
        testPortfolio(100000, 4, 0.05, 0.05);
    }

    /**
     *
     * @param maxAmount Maximum amount of investments.
     * @param term Term (period of investments).
     * @param loss Acceptable loss.
     * @param probability Probability of acceptable loss.
     * @throws POException In case of any error.
     */
    private void testPortfolio(double maxAmount, int term, double loss, double probability) throws POException {
        PortfolioFinder pf = new PortfolioFinderImpl();

        Risk risk = new Risk();

        // 10% loss
        risk.setLoss(loss);
        // Probability 5%
        risk.setProbability(probability);

        PortfolioTask task = new PortfolioTask();

        task.setInstrument(TestUtils.TEST_INSTRUMENTS);
        task.setTerm(term);
        // Maximize profit.
        task.setType(PortfolioTaskType.MAXIMIZE_PROFIT);
        // Want to invest 20 000.
        task.setMaxAmount(maxAmount);
        task.setRisk(risk);
        task.setLossScale(SolutionUtil.DFLT_LOSS_SCALE);

        System.out.println(SolutionUtil.printPortfolio(pf.find(task)));
    }

}
