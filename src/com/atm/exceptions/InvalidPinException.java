package com.atm.exceptions;

/**
 * Exception thrown when an incorrect PIN is entered for a valid card.
 */
public class InvalidPinException extends Exception {

    public InvalidPinException(String errorMsg) {
        super(errorMsg);
    }
}


