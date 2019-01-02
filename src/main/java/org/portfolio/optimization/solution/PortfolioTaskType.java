package org.portfolio.optimization.solution;

public enum PortfolioTaskType {
    /** Maximize profit, with the risk not exceeding certain level defined by user.**/
    MAXIMIZE_PROFIT,

    /** Minimize risk, with profit not lower than certain level set by user.*/
    MINIMIZE_RISK // Not yet implemented
}
