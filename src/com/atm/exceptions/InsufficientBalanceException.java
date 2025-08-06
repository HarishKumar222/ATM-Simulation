package com.atm.exceptions;

/**
 * Exception thrown when a withdrawal request exceeds the account balance.
 */
public class InsufficientBalanceException extends Exception {

    public InsufficientBalanceException(String errorMsg) {
        super(errorMsg);
    }
}


