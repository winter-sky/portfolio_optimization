package org.portfolio.optimization.solution;

import org.portfolio.optimization.POException;
import org.portfolio.optimization.potfolio.Portfolio;

public interface PortfolioFinder {
    /**
     * Finds the portfolio for the given input data.
     *
     * @param task
     * @return
     * @throws POException
     */
    Portfolio find(PortfolioTask task) throws POException;
}
