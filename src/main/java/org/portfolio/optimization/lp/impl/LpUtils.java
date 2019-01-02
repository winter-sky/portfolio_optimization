package org.portfolio.optimization.lp.impl;

import org.portfolio.optimization.lp.Relation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Provides utility methods
 */
public class LpUtils {
    private static final Logger log = LoggerFactory.getLogger(LpUtils.class);

    public static void printConstraint(String name, double[] coeff, Relation relation, double value) {
        if (name != null) {
            log.debug("Constraint added " + name + ", [c=" + Arrays.toString(coeff) + ", relation=" + relation
                + ", value=" + value + ']');
        }
        else {
            log.debug("Constraint added [c=" + Arrays.toString(coeff) + ", relation=" + relation + ", value="
                + value + ']');
        }
    }
}
