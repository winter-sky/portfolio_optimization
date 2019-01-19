package org.portfolio.optimization;

import org.junit.Test;
import org.portfolio.optimization.model.Instrument;
import org.portfolio.optimization.model.InstrumentType;
import org.portfolio.optimization.potfolio.Portfolio;
import org.portfolio.optimization.potfolio.PortfolioInstrument;
import org.portfolio.optimization.solution.PortfolioFinder;
import org.portfolio.optimization.solution.PortfolioTask;
import org.portfolio.optimization.solution.PortfolioTaskType;
import org.portfolio.optimization.solution.Risk;
import org.portfolio.optimization.solution.impl.PortfolioFinderImpl;
import org.portfolio.optimization.solution.impl.SolutionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static org.portfolio.optimization.solution.impl.SolutionUtil.*;

public class RealDataTest {
    private static final Logger log = LoggerFactory.getLogger(RealDataTest.class);

    private static final int SCALE_FACTOR = 10000;

    private static final int YIELD_CURVE_LENGTH = 8;

    private static final AtomicLong idGen = new AtomicLong();

    /**
     * Yield of actions by the beginning of each year comparing to 2010-01-01.
     */
    private static final double[] YIELD_VTB = new double[] {
        45.99,  // 2011
        -17.44, // 2012
        -24.55, // 2013
        -34.57, // 2014
        -9.50,  // 2015
        2.95,   // 2016
        -1.78,  // 2017
        -32.85  // 2018
    };

    /**
     * Yield of actions by the beginning of each year comparing to 2010-01-01.
     */
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

    /**
     * Yield of actions by the beginning of each year comparing to 2010-01-01.
     */
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

    /**
     * Yield of actions by the beginning of each year comparing to 2010-01-01.
     */
    private static final double[] YIELD_NORNIKEL = new double[] {
        57.10,
        3.74,
        23.52,
        9.32,
        94.50,
        89.32,
        113.03,
        137.77
    };

    /**
     * Yield of actions by the beginning of each year comparing to 2010-01-01.
     */
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

    /**
     * Yield of actions by the beginning of each year comparing to 2010-01-01.
     */
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

    /**
     * Yield curve of obligations.
     */
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

    /** Price of action for the beginning of calculation period (2018-01-01). */
    private static final double LOT_VTB = 0.05;

    /** Price of action for the beginning of calculation period (2018-01-01). */
    private static final double LOT_GAZPROM = 132.20;

    /** Price of action for the beginning of calculation period (2018-01-01). */
    private static final double LOT_LUKOIL = 3421.50;

    /** Price of action for the beginning of calculation period (2018-01-01). */
    private static final double LOT_NORNIKEL = 11302.00;

    /** Price of action for the beginning of calculation period (2018-01-01). */
    private static final double LOT_ROSNEFT = 297.95;

    /** Price of action for the beginning of calculation period (2018-01-01). */
    private static final double LOT_SBERBANK = 224.65;

    /** Price of obligation. */
    private static final double LOT_OBLIGATION_BANK_ROSSII = 1000;

    /** String name to identify financial instrument. */
    private static final String NAME_VTB="VTB";

    /** String name to identify financial instrument. */
    private static final String NAME_GAZPROM="Gazprom";

    /** String name to identify financial instrument. */
    private static final String NAME_LUKOIL="Lukoil";

    /** String name to identify financial instrument. */
    private static final String NAME_NORNIKEL="Nornikel";

    /** String name to identify financial instrument. */
    private static final String NAME_ROSNEFT="Rosneft";

    /** String name to identify financial instrument. */
    private static final String NAME_SBERBANK="Sberbank";

    /** String name to identify financial instrument. */
    private static final String NAME_OBLIGATION_BANK_ROSSII="Gosobligacii";

    private static Map<String, Double> prices2019 = new HashMap();

    /**
     * Prices of tested financial instruments by 2019-01-01.
     */
    static {
        prices2019.put(NAME_VTB, 0.03);
        prices2019.put(NAME_GAZPROM, 153.50);
        prices2019.put(NAME_LUKOIL, 4997.00);
        prices2019.put(NAME_NORNIKEL, 13039.00);
        prices2019.put(NAME_ROSNEFT, 425.85);
        prices2019.put(NAME_SBERBANK, 196.99);
        prices2019.put(NAME_OBLIGATION_BANK_ROSSII, 1064.9);
    }


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

        inst.setId(idGen.incrementAndGet());
        inst.setMinimalLot(minLot);
        inst.setName(name);
        inst.setYieldCurve(yieldCurve);
        inst.setRiskCurve(riskCurve);
        inst.setInstrumentType(InstrumentType.ACTION);

        log.info("Instrument data calculated [instrument={}]", inst);

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

        inst.setId(idGen.incrementAndGet());
        inst.setName(name);
        inst.setMinimalLot(minLot);
        inst.setYieldCurve(yieldCurveRelative);
        inst.setRiskCurve(riskCurve);
        inst.setInstrumentType(InstrumentType.OBLIGATION);


        log.info("Instrument data calculated [instrument={}]", inst);

        return inst;
    }

    private PortfolioTask prepareTaskMinRisk(double maxAmount, int term, double minYield) {
        Instrument[] instrs = new Instrument[6];

        instrs[0] = prepareInstrumentAction("VTB", YIELD_VTB, LOT_VTB, DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
        instrs[1] = prepareInstrumentAction("Gazprom", YIELD_GAZPROM, LOT_GAZPROM, DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
        instrs[2] = prepareInstrumentAction("Lukoil", YIELD_LUKOIL, LOT_LUKOIL, DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
        instrs[3] = prepareInstrumentAction("Rosneft", YIELD_ROSNEFT, LOT_ROSNEFT, DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
        instrs[4] = prepareInstrumentAction("Sberbank", YIELD_SBERBANK, LOT_SBERBANK, DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
        instrs[5] = prepareInstrumentObligation("Gosobligacii", YIELD_CURVE_OBLIGATIONS_BANK_ROSSII, LOT_OBLIGATION_BANK_ROSSII, DFLT_LOSS_SCALE);

        PortfolioTask task = new PortfolioTask();

        task.setType(PortfolioTaskType.MINIMIZE_RISK);
        task.setInstrument(instrs);
        task.setMinYield(minYield);
        task.setLossScale(DFLT_LOSS_SCALE);
        task.setMaxAmount(maxAmount);
        task.setTerm(term);

        return task;
    }

    private PortfolioTask prepareTaskMaxProfit(double maxAmount, int term, double loss, double probability) {
        Instrument[] instrs = new Instrument[6];

        instrs[0] = prepareInstrumentObligation("Gosobligacii", YIELD_CURVE_OBLIGATIONS_BANK_ROSSII, LOT_OBLIGATION_BANK_ROSSII, DFLT_LOSS_SCALE);
        instrs[1] = prepareInstrumentAction("VTB", YIELD_VTB, LOT_VTB, DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
        instrs[2] = prepareInstrumentAction("Gazprom", YIELD_GAZPROM, LOT_GAZPROM, DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
        instrs[3] = prepareInstrumentAction("Lukoil", YIELD_LUKOIL, LOT_LUKOIL, DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
        instrs[4] = prepareInstrumentAction("Rosneft", YIELD_ROSNEFT, LOT_ROSNEFT, DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);
        instrs[5] = prepareInstrumentAction("Sberbank", YIELD_SBERBANK, LOT_SBERBANK, DFLT_LOSS_SCALE, YIELD_CURVE_LENGTH);

        Risk risk = new Risk();

        risk.setProbability(probability);
        risk.setLoss(loss);

        PortfolioTask task = new PortfolioTask();

        task.setType(PortfolioTaskType.MAXIMIZE_PROFIT);
        task.setInstrument(instrs);
        task.setRisk(risk);
        task.setLossScale(DFLT_LOSS_SCALE);
        task.setMaxAmount(maxAmount);
        task.setTerm(term);

        return task;
    }

    private void calcReal(Portfolio portfolio) {
        List<PortfolioInstrument> pis = portfolio.getPortfolioInstruments();

        double realAmount = 0;

        for (PortfolioInstrument pi : pis) {
            realAmount += pi.getQuantity() * prices2019.get(pi.getInstrument().getName());
        }

        realAmount = SolutionUtil.round(realAmount);

        double yield = SolutionUtil.round((realAmount - portfolio.getAmount()) * 100 / portfolio.getAmount());

        double income = SolutionUtil.round(realAmount - portfolio.getAmount());

        System.out.println("Real yield: " + yield + '%');
        System.out.println("Real income: " + income);
        System.out.println("Real amount at term: " + realAmount);

    }

    @Test
    public void test1() throws Exception {
        testMinRisk(100000, 2, 0.10);
        testMinRisk(100000, 2, 0.12);
        testMinRisk(100000, 2, 0.15);
        testMinRisk(100000, 2, 0.18);
    }

    private void testMinRisk(double maxAmount, int term, double minYield) throws POException {
        PortfolioTask task = prepareTaskMinRisk(maxAmount, term, minYield);

        PortfolioFinder finder = new PortfolioFinderImpl();

        Portfolio portfolio = finder.find(task);

        System.out.println(printPortfolio(portfolio));

        calcReal(portfolio);
    }

    @Test
    public void test2() throws Exception {
        testMaxProfit(100000, 2, 0.05, 0.1);
    }

    private void testMaxProfit(double maxAmount, int term, double loss, double probability) throws POException {
        PortfolioTask task = prepareTaskMaxProfit(maxAmount, term, loss, probability);

        PortfolioFinder finder = new PortfolioFinderImpl();

        Portfolio portfolio = finder.find(task);

        System.out.println(printPortfolio(portfolio));

        calcReal(portfolio);
    }
}
