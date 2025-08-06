package com.atm.exceptions;

/**
 * Exception thrown when a user exceeds the allowed number of incorrect PIN attempts.
 */
public class IncorrectPinLimitReachedException extends Exception {

    public IncorrectPinLimitReachedException(String errorMsg) {
        super(errorMsg);
    }
}

