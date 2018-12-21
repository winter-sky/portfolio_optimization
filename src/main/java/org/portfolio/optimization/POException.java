package org.portfolio.optimization;

public class POException extends Exception {
    public POException(String message) {
        super(message);
    }

    public POException(String message, Throwable cause) {
        super(message, cause);
    }
}
