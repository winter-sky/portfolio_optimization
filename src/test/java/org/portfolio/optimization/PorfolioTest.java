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

public class PorfolioTest {
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
    public void testPortfolio() throws Exception {
        PortfolioFinder pf = new PortfolioFinderImpl();

        Risk risk = new Risk();

        // 10% loss
        risk.setLoss(0.9);
        // Probability 5%
        risk.setProbability(0.05);

        PortfolioTask task = new PortfolioTask();

        task.setInstrument(TEST_INSTRUMENTS);
        task.setTerm(4);
        // Maximize profit.
        task.setType(PortfolioTaskType.MAXIMIZE_PROFIT);
        // Want to invest 20 000.
        task.setMaxAmount(20000);
        task.setRisk(risk);
        task.setLossScale(SolutionUtil.DFLT_LOSS_SCALE);

        System.out.println(pf.find(task));
    }

}
