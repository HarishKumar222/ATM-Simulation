package com.atm.exceptions;

/**
 * Exception thrown when the ATM machine does not have enough cash
 * to process a withdrawal or deposit.
 */
public class InsufficientMachineBalanceException extends Exception {

    public InsufficientMachineBalanceException(String errorMsg) {
        super(errorMsg);
    }
}


