package com.atm.exceptions;

/**
 * Exception thrown when a non-operator user attempts to access
 * operator-only functionality in the ATM system.
 */
public class NotAOperatorException extends Exception {

    public NotAOperatorException(String errorMsg) {
        super(errorMsg);
    }
}


