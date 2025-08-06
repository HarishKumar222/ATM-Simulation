package com.atm.exceptions;

/**
 * Exception thrown when an entered deposit or withdrawal amount
 * is invalid or not in the required denomination.
 */
public class InvalidAmountException extends Exception {

    public InvalidAmountException(String errorMsg) {
        super(errorMsg);
    }
}

