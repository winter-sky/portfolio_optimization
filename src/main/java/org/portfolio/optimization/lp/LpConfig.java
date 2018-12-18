package org.portfolio.optimization.lp;

import org.portfolio.optimization.lp.impl.LpSolveConfig;

public class LpConfig {
    private static LpConfig instance = new LpConfig();

    private LpSolveConfig lpSolveConfig;

    public static LpConfig getConfig() {
        return instance;
    }


    public LpSolveConfig getLpSolveConfig() {
        return lpSolveConfig;
    }

    public void setLpSolveConfig(LpSolveConfig lpSolveConfig) {
        this.lpSolveConfig = lpSolveConfig;
    }
}
