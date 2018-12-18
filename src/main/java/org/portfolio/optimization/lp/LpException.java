package org.portfolio.optimization.lp;

/**
 * TBD: add comments for LpException.java.
 */
public class LpException extends Exception {
    public LpException(String msg) {
        super(msg);
    }

    public LpException(String msg, Throwable cause) {
        super(msg, cause);
    }

    @Override
    public String toString() {
        return "LpException{}";
    }
}
