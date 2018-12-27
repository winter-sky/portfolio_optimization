package org.portfolio.optimization;

import org.junit.Test;
import org.portfolio.optimization.model.Instrument;
import org.portfolio.optimization.model.InstrumentType;
import org.portfolio.optimization.model.InstrumentUtil;
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

    private static Instrument[] TEST_INSTRUMENTS = new Instrument[]{
        InstrumentUtil.mkInstrument(
            1,
            "Sberbank",
            InstrumentType.ACTION,
            120.0,
            new double[] {0.01, 0.012, 0.015, 0.018, 0.020, 0.021},
            new double[] {0, 0.05, 0.1, 0.12, 0.14, 0.18, 0.2, 0.21, 0.22, 0.23}
            ),
        InstrumentUtil.mkInstrument(
            1,
            "Gazprom",
            InstrumentType.ACTION,
            1050.0,
            new double[] {0.01, 0.014, 0.016, 0.019, 0.024, 0.027},
            new double[] {0, 0.05, 0.1, 0.12, 0.18, 0.28, 0.3, 0.35, 0.4, 0.45}
        ),
        InstrumentUtil.mkInstrument(
            1,
            "Rosneft",
            InstrumentType.ACTION,
            780.0,
            new double[] {0.05, 0.008, 0.014, 0.015, 0.017, 0.020},
            new double[] {0, 0.05, 0.1, 0.12, 0.14, 0.18, 0.19, 0.2, 0.2, 0.2}
        ),
        InstrumentUtil.mkInstrument(
            1,
            "Bank Rossii",
            InstrumentType.OBLIGATION,
            2030.0,
            new double[] {0.05, 0.008, 0.014, 0.015, 0.017, 0.020},
            new double[] {0, 0., 0., 0., 0., 0.01, 0.01, 0.01, 0.01, 0.01}
        )
    };

    @Test
    public void testPortfolio1() throws Exception {
        testPortfolio(20000, 4, 0.9, 0.05);
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
        testPortfolio(100000, 4, 0.9, 0.01);
        testPortfolio(100000, 4, 0.9, 0.05);
        testPortfolio(100000, 4, 0.9, 0.5);
    }

    /**
     * In this example, high risk is accepted
     *
     * @throws Exception
     */
    @Test
    public void testPortfolio5() throws Exception {
        testPortfolio(100000, 4, 0.5, 0.05);
        testPortfolio(100000, 4, 0.9, 0.05);
        testPortfolio(100000, 4, 0.95, 0.05);
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

        task.setInstrument(TEST_INSTRUMENTS);
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
