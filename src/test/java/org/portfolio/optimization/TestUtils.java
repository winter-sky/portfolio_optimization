package org.portfolio.optimization;

import org.portfolio.optimization.model.Instrument;
import org.portfolio.optimization.model.InstrumentType;
import org.portfolio.optimization.model.InstrumentUtil;
import org.portfolio.optimization.solution.impl.SolutionUtil;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class TestUtils {
    public static Instrument[] TEST_INSTRUMENTS = new Instrument[]{
        InstrumentUtil.mkInstrument(
            1,
            "Sberbank",
            InstrumentType.ACTION,
            120.0,
            new double[] {0.01, 0.012, 0.015, 0.018, 0.020, 0.021},
            new double[] {0.78, 0.1, 0.05, 0.01, 0.008, 0.005, 0.004, 0.003, 0.001, 0.0001}
            ),
        InstrumentUtil.mkInstrument(
            1,
            "Gazprom",
            InstrumentType.ACTION,
            1050.0,
            new double[] {0.01, 0.014, 0.016, 0.019, 0.024, 0.027},
            new double[] {0.72, 0.13, 0.09, 0.07, 0.01, 0.009, 0.008, 0.007, 0.006, 0.0005}
        ),
        InstrumentUtil.mkInstrument(
            1,
            "Rosneft",
            InstrumentType.ACTION,
            780.0,
            new double[] {0.05, 0.008, 0.014, 0.015, 0.017, 0.020},
            new double[] {0.8, 0.08, 0.05, 0.02, 0.006, 0.003, 0.002, 0.001, 0.0005, 0.0001}
        ),
        InstrumentUtil.mkInstrument(
            1,
            "Bank Rossii",
            InstrumentType.OBLIGATION,
            2030.0,
            new double[] {0.05, 0.008, 0.014, 0.015, 0.017, 0.020},
            new double[] {0.999, 0.001, 0.001, 0.001, 0.001, 0.001, 0.001, 0.001, 0.001, 0.001}
        )
    };

    public static void assertArraysEquals(double[][] arr1, double[][] arr2) {
        assertEquals(arr1.length, arr2.length);

        for (int i = 0; i < arr1.length ; i++) {
            for (int j = 0; j < arr1[i].length; j++) {
                double val1 = SolutionUtil.roundProb(arr1[i][j]);
                double val2 = SolutionUtil.roundProb(arr2[i][j]);

                assertEquals("Different elements [i=" + i + ", j=" + j + ", val1=" + val1 + ", val2=" + val2 + ']',
                    0, Double.compare(val1, val2));
            }
        }
    }

    public static void print(double[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(Arrays.toString(arr[i]));
        }
    }
}
