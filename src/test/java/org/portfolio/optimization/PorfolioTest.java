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
    public void testPortfolioMaxProfit1() throws Exception {
        testPortfolioMaxProfit(20000, 4, 0.05, 0.05);
    }

    /**
     * In this example, low risk is required, so only Obligations should be included
     *
     * @throws Exception
     */
    @Test
    public void testPortfolioMaxProfit2() throws Exception {
        testPortfolioMaxProfit(100000, 2, 0.05, 0.05);
    }

    /**
     * In this example, high risk is accepted
     *
     * @throws Exception
     */
    @Test
    public void testPortfolioMaxProfit3() throws Exception {
        testPortfolioMaxProfit(15000, 2, 0.01, 0.05);
    }

    /**
     * In this example, high risk is accepted
     *
     * @throws Exception
     */
    @Test
    public void testPortfolioMaxProfit4() throws Exception {
        testPortfolioMaxProfit(100000, 4, 0.1, 0.01);
        testPortfolioMaxProfit(100000, 4, 0.1, 0.05);
        testPortfolioMaxProfit(100000, 4, 0.1, 0.5);
    }

    /**
     * In this example, high risk is accepted
     *
     * @throws Exception
     */
    @Test
    public void testPortfolioMaxProfit5() throws Exception {
        testPortfolioMaxProfit(100000, 4, 0.5, 0.05);
        testPortfolioMaxProfit(100000, 4, 0.1, 0.05);
        testPortfolioMaxProfit(100000, 4, 0.05, 0.05);
    }

    @Test
    public void testPortfolioMinRisk1() throws Exception {
        testPortfolioMinRisk(100000, 4, 0.015);
        testPortfolioMinRisk(100000, 4, 0.018);
        testPortfolioMinRisk(100000, 4, 0.02);
        testPortfolioMinRisk(100000, 4, 0.022);
        testPortfolioMinRisk(100000, 4, 0.024);
    }
    /**
     *
     * @param maxAmount Maximum amount of investments.
     * @param term Term (period of investments).
     * @param loss Acceptable loss.
     * @param probability Probability of acceptable loss.
     * @throws POException In case of any error.
     */
    private void testPortfolioMaxProfit(
        double maxAmount,
        int term,
        double loss,
        double probability) throws POException {
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

    /**
     *
     * @param maxAmount Maximum amount of investments.
     * @param term Term (period of investments).
     * @param minYield Minimal yield set by user.
     * @throws POException In case of any error.
     */
    private void testPortfolioMinRisk(
        double maxAmount,
        int term,
        double minYield) throws POException {
        PortfolioFinder pf = new PortfolioFinderImpl();

        PortfolioTask task = new PortfolioTask();

        task.setInstrument(TestUtils.TEST_INSTRUMENTS);
        task.setTerm(term);
        // Maximize profit.
        task.setType(PortfolioTaskType.MINIMIZE_RISK);
        // Want to invest 20 000.
        task.setMaxAmount(maxAmount);
        task.setMinYield(minYield);
        task.setLossScale(SolutionUtil.DFLT_LOSS_SCALE);

        System.out.println(SolutionUtil.printPortfolio(pf.find(task)));
    }
}
