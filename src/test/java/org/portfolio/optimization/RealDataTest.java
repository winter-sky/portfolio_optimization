package org.portfolio.optimization;

import org.junit.Test;
import org.portfolio.optimization.model.Instrument;
import org.portfolio.optimization.potfolio.Portfolio;
import org.portfolio.optimization.solution.PortfolioFinder;
import org.portfolio.optimization.solution.PortfolioTask;
import org.portfolio.optimization.solution.PortfolioTaskType;
import org.portfolio.optimization.solution.impl.PortfolioFinderImpl;
import org.portfolio.optimization.solution.impl.SolutionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class RealDataTest {
    private static final Logger log = LoggerFactory.getLogger(RealDataTest.class);

    private static final int SCALE_FACTOR = 10000;

    private static final int YIELD_CURVE_LENGTH = 8;

    public static final double[] YIELD_VTB = new double[] {
        45.99,
        -17.44,
        -24.55,
        -34.57,
        -9.50,
        2.95,
        -1.78,
        -32.85
    };

    public static final double[] YIELD_GAZPROM = new double[] {
        3.23,
        -7.29,
        -22.41,
        -27.30,
        -26.51,
        -28.87,
        -18.36,
        -30.73,
    };

    public static final double[] YIELD_LUKOIL = new double[] {
        7.94,
        0.05,
        16.71,
        14.60,
        39.96,
        26.88,
        99.95,
        98.30

    };

    public static final double[] YIELD_MAGNIT = new double[] {
        96.60,
        43.61,
        125.92,
        322.30,
        373.55,
        418.52,
        406.64,
        196.38

    };

    public static final double[] YIELD_ROSNEFT = new double[] {
        -13.84,
        -18.01,
        -0.36,
        -6.28,
        -23.77,
        -6.05,
        50.62,
        10.99
    };

    public static final double[] YIELD_SBERBANK = new double[] {
        22.58,
        -4.47,
        13.64,
        13.57,
        -29.88,
        12.81,
        101.23,
        158.81,
    };

    private static final double LOT_VTB = 0.07;

    private static final double LOT_GAZPROM = 190.86;

    private static final double LOT_LUKOIL = 1727.80;

    private static final double LOT_MAGNIT = 2188.03;

    private static final double LOT_ROSNEFT = 268.45;

    private static final double LOT_SBERBANK = 86.80;


    private static Instrument prepareActionInstrument(String name, double[] yield, double minLot, double[] lossScale, int n) {
        // Calculate yield per year using total yield per period.
        double totalYield = SolutionUtil.round(yield[yield.length - 1] / (yield.length * 100), SCALE_FACTOR);

        int len = lossScale.length;

        int[] losses = new int[len];

        for (int i = 0; i < yield.length; i++) {
            double loss = SolutionUtil.round(totalYield - yield[i] / 100, SCALE_FACTOR);

            if (Double.compare(loss, 0) > 0) {
                int idx = SolutionUtil.getLossIndex(loss, lossScale);

                losses[idx]++;
            }
            else {
                losses[0]++;
            }
        }

        double[] riskCurve = new double[len];

        for (int i = 0; i < len ; i++) {
            riskCurve[i] = SolutionUtil.roundProb((double)losses[i] / yield.length);
        }

        double[] yieldCurve = new double[n];

        for (int i = 0; i < n; i++) {
            yieldCurve[i] = totalYield;
        }

        Instrument inst = new Instrument();

        inst.setMinimalLot(minLot);
        inst.setName(name);
        inst.setYieldCurve(yieldCurve);
        inst.setRiskCurve(riskCurve);

        log.info("Instrument build [instrument={}]", inst);

        return inst;
    }

    private PortfolioTask prepareTask() {
//        Instrument[] instrs = new Instrument[6];
//
//        instrs[0] = prepareActionInstrument("VTB", YIELD_VTB, LOT_VTB, SolutionUtil.DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
//        instrs[1] = prepareActionInstrument("Gazprom", YIELD_GAZPROM, LOT_GAZPROM, SolutionUtil.DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
//        instrs[2] = prepareActionInstrument("Lukoil", YIELD_LUKOIL, LOT_LUKOIL, SolutionUtil.DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
//        instrs[3] = prepareActionInstrument("Magnit", YIELD_MAGNIT, LOT_MAGNIT, SolutionUtil.DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
//        instrs[4] = prepareActionInstrument("Rosneft", YIELD_ROSNEFT, LOT_ROSNEFT, SolutionUtil.DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
//        instrs[5] = prepareActionInstrument("Sberbank", YIELD_SBERBANK, LOT_SBERBANK, SolutionUtil.DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);

        Instrument[] instrs = new Instrument[5];

        instrs[0] = prepareActionInstrument("VTB", YIELD_VTB, LOT_VTB, SolutionUtil.DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
        instrs[1] = prepareActionInstrument("Gazprom", YIELD_GAZPROM, LOT_GAZPROM, SolutionUtil.DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
        instrs[2] = prepareActionInstrument("Lukoil", YIELD_LUKOIL, LOT_LUKOIL, SolutionUtil.DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
        //instrs[3] = prepareActionInstrument("Magnit", YIELD_MAGNIT, LOT_MAGNIT, SolutionUtil.DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
        instrs[3] = prepareActionInstrument("Rosneft", YIELD_ROSNEFT, LOT_ROSNEFT, SolutionUtil.DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
        instrs[4] = prepareActionInstrument("Sberbank", YIELD_SBERBANK, LOT_SBERBANK, SolutionUtil.DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);

        PortfolioTask task = new PortfolioTask();

        task.setType(PortfolioTaskType.MINIMIZE_RISK);
        task.setInstrument(instrs);
        task.setMinYield(0.15);
        task.setLossScale(SolutionUtil.DFLT_LOSS_SCALE);
        task.setMaxAmount(100000);

        return task;
    }

    @Test
    public void test1() throws Exception {
        PortfolioTask task = prepareTask();

        PortfolioFinder finder = new PortfolioFinderImpl();

        Portfolio portfolio = finder.find(task);

        System.out.println(SolutionUtil.printPortfolio(portfolio));
    }
}
