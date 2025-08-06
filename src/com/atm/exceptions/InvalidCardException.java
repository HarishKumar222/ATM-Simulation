package com.atm.exceptions;

/**
 * Exception thrown when a provided card number is not recognized
 * or not supported by the ATM system.
 */
public class InvalidCardException extends Exception {

    public InvalidCardException(String errorMsg) {
        super(errorMsg);
    }
}


