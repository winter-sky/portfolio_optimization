package org.portfolio.optimization;

import org.junit.Test;
import org.portfolio.optimization.model.Instrument;
import org.portfolio.optimization.potfolio.Portfolio;
import org.portfolio.optimization.solution.PortfolioFinder;
import org.portfolio.optimization.solution.PortfolioTask;
import org.portfolio.optimization.solution.PortfolioTaskType;
import org.portfolio.optimization.solution.Risk;
import org.portfolio.optimization.solution.impl.PortfolioFinderImpl;
import org.portfolio.optimization.solution.impl.SolutionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.portfolio.optimization.solution.impl.SolutionUtil.*;

public class RealDataTest {
    private static final Logger log = LoggerFactory.getLogger(RealDataTest.class);

    private static final int SCALE_FACTOR = 10000;

    private static final int YIELD_CURVE_LENGTH = 8;

    private static final double[] YIELD_VTB = new double[] {
        45.99,
        -17.44,
        -24.55,
        -34.57,
        -9.50,
        2.95,
        -1.78,
        -32.85
    };

    private static final double[] YIELD_GAZPROM = new double[] {
        3.23,
        -7.29,
        -22.41,
        -27.30,
        -26.51,
        -28.87,
        -18.36,
        -30.73,
    };

    private static final double[] YIELD_LUKOIL = new double[] {
        7.94,
        0.05,
        16.71,
        14.60,
        39.96,
        26.88,
        99.95,
        98.30

    };

    private static final double[] YIELD_MAGNIT = new double[] {
        96.60,
        43.61,
        125.92,
        322.30,
        373.55,
        418.52,
        406.64,
        196.38

    };

    private static final double[] YIELD_ROSNEFT = new double[] {
        -13.84,
        -18.01,
        -0.36,
        -6.28,
        -23.77,
        -6.05,
        50.62,
        10.99
    };

    private static final double[] YIELD_SBERBANK = new double[] {
        22.58,
        -4.47,
        13.64,
        13.57,
        -29.88,
        12.81,
        101.23,
        158.81,
    };

    private static final double[] YIELD_CURVE_OBLIGATIONS_BANK_ROSSII = new double[] {
        6.16,
        6.27,
        6.38,
        6.49,
        6.79,
        6.93,
        7.15,
        7.35,
    };

    private static final double LOT_VTB = 0.07;

    private static final double LOT_GAZPROM = 190.86;

    private static final double LOT_LUKOIL = 1727.80;

    private static final double LOT_MAGNIT = 2188.03;

    private static final double LOT_ROSNEFT = 268.45;

    private static final double LOT_SBERBANK = 86.80;

    private static final double LOT_OBLIGATION_BANK_ROSSII = 1000;


    private static Instrument prepareInstrumentAction(String name, double[] yield, double minLot, double[] lossScale, int n) {
        // Calculate yield per year using total yield per period.
        double totalYield = round(yield[yield.length - 1] / (yield.length * 100), SCALE_FACTOR);

        int len = lossScale.length;

        int[] losses = new int[len];

        for (int i = 0; i < yield.length; i++) {
            double loss = round(totalYield - yield[i] / 100, SCALE_FACTOR);

            if (Double.compare(loss, 0) > 0) {
                int idx = getLossIndex(loss, lossScale);

                losses[idx]++;
            }
            else {
                losses[0]++;
            }
        }

        double[] riskCurve = new double[len];

        for (int i = 0; i < len ; i++) {
            riskCurve[i] = roundProb((double)losses[i] / yield.length);
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

    private static Instrument prepareInstrumentObligation(
        String name, double[] yieldCurve, double minLot, double[] lossScale) {
        double[] riskCurve = new double[lossScale.length];

        riskCurve[0] = 1;

        double[] yieldCurveRelative = new double[yieldCurve.length];

        // Convert from percents.
        for (int i = 0; i < yieldCurve.length; i++) {
            yieldCurveRelative[i] = SolutionUtil.round(yieldCurve[i] / 100, SCALE_FACTOR);
        }

        Instrument inst = new Instrument();

        inst.setName(name);
        inst.setMinimalLot(minLot);
        inst.setYieldCurve(yieldCurveRelative);
        inst.setRiskCurve(riskCurve);

        log.info("Instrument build [instrument={}]", inst);

        return inst;
    }

    private PortfolioTask prepareTask() {
//        Instrument[] instrs = new Instrument[6];
//
//        instrs[0] = prepareInstrumentAction("VTB", YIELD_VTB, LOT_VTB, SolutionUtil.DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
//        instrs[1] = prepareInstrumentAction("Gazprom", YIELD_GAZPROM, LOT_GAZPROM, SolutionUtil.DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
//        instrs[2] = prepareInstrumentAction("Lukoil", YIELD_LUKOIL, LOT_LUKOIL, SolutionUtil.DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
//        instrs[3] = prepareInstrumentAction("Magnit", YIELD_MAGNIT, LOT_MAGNIT, SolutionUtil.DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
//        instrs[4] = prepareInstrumentAction("Rosneft", YIELD_ROSNEFT, LOT_ROSNEFT, SolutionUtil.DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
//        instrs[5] = prepareInstrumentAction("Sberbank", YIELD_SBERBANK, LOT_SBERBANK, SolutionUtil.DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);

        Instrument[] instrs = new Instrument[6];

        instrs[0] = prepareInstrumentAction("VTB", YIELD_VTB, LOT_VTB, DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
        instrs[1] = prepareInstrumentAction("Gazprom", YIELD_GAZPROM, LOT_GAZPROM, DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
        instrs[2] = prepareInstrumentAction("Lukoil", YIELD_LUKOIL, LOT_LUKOIL, DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
        //instrs[3] = prepareInstrumentAction("Magnit", YIELD_MAGNIT, LOT_MAGNIT, SolutionUtil.DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
        instrs[3] = prepareInstrumentAction("Rosneft", YIELD_ROSNEFT, LOT_ROSNEFT, DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
        instrs[4] = prepareInstrumentAction("Sberbank", YIELD_SBERBANK, LOT_SBERBANK, DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
        instrs[5] = prepareInstrumentObligation("Gosobligacii", YIELD_CURVE_OBLIGATIONS_BANK_ROSSII, LOT_OBLIGATION_BANK_ROSSII, DFLT_LOSS_SCALE);

        PortfolioTask task = new PortfolioTask();

        task.setType(PortfolioTaskType.MINIMIZE_RISK);
        task.setInstrument(instrs);
        task.setMinYield(0.15);
        task.setLossScale(DFLT_LOSS_SCALE);
        task.setMaxAmount(100000);
        task.setTerm(2);

        return task;
    }

    private PortfolioTask prepareTask2() {
//        Instrument[] instrs = new Instrument[6];
//
//        instrs[0] = prepareInstrumentAction("VTB", YIELD_VTB, LOT_VTB, SolutionUtil.DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
//        instrs[1] = prepareInstrumentAction("Gazprom", YIELD_GAZPROM, LOT_GAZPROM, SolutionUtil.DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
//        instrs[2] = prepareInstrumentAction("Lukoil", YIELD_LUKOIL, LOT_LUKOIL, SolutionUtil.DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
//        instrs[3] = prepareInstrumentAction("Magnit", YIELD_MAGNIT, LOT_MAGNIT, SolutionUtil.DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
//        instrs[4] = prepareInstrumentAction("Rosneft", YIELD_ROSNEFT, LOT_ROSNEFT, SolutionUtil.DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
//        instrs[5] = prepareInstrumentAction("Sberbank", YIELD_SBERBANK, LOT_SBERBANK, SolutionUtil.DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);

        Instrument[] instrs = new Instrument[6];

        instrs[0] = prepareInstrumentAction("VTB", YIELD_VTB, LOT_VTB, DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
        instrs[1] = prepareInstrumentAction("Gazprom", YIELD_GAZPROM, LOT_GAZPROM, DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
        instrs[2] = prepareInstrumentAction("Lukoil", YIELD_LUKOIL, LOT_LUKOIL, DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
        //instrs[3] = prepareInstrumentAction("Magnit", YIELD_MAGNIT, LOT_MAGNIT, SolutionUtil.DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
        instrs[3] = prepareInstrumentAction("Rosneft", YIELD_ROSNEFT, LOT_ROSNEFT, DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
        instrs[4] = prepareInstrumentAction("Sberbank", YIELD_SBERBANK, LOT_SBERBANK, DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
        instrs[5] = prepareInstrumentObligation("Gosobligacii", YIELD_CURVE_OBLIGATIONS_BANK_ROSSII, LOT_OBLIGATION_BANK_ROSSII, DFLT_LOSS_SCALE);

        Risk risk = new Risk();

        risk.setProbability(0.001);
        risk.setLoss(0.05);

        PortfolioTask task = new PortfolioTask();

        task.setType(PortfolioTaskType.MAXIMIZE_PROFIT);
        task.setInstrument(instrs);
        task.setRisk(risk);
        task.setLossScale(DFLT_LOSS_SCALE);
        task.setMaxAmount(100000);
        task.setTerm(2);

        return task;
    }

    @Test
    public void test1() throws Exception {
        PortfolioTask task = prepareTask();

        PortfolioFinder finder = new PortfolioFinderImpl();

        Portfolio portfolio = finder.find(task);

        System.out.println(printPortfolio(portfolio));
    }

    @Test
    public void test2() throws Exception {
        PortfolioTask task = prepareTask2();

        PortfolioFinder finder = new PortfolioFinderImpl();

        Portfolio portfolio = finder.find(task);

        System.out.println(printPortfolio(portfolio));
    }
}
