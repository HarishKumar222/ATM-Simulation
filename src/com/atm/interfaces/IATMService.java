package com.atm.interfaces;

import com.atm.exceptions.InsufficientBalanceException;
import com.atm.exceptions.InvalidAmountException;
import com.atm.exceptions.NotAOperatorException;
import com.atm.exceptions.InsufficientMachineBalanceException;

/**
 * ATM Service interface that defines core operations for both
 * regular users and operators.
 */
public interface IATMService {

    /**
     * Gets the user type (operator or normal user).
     *
     * @return the user type as a String
     * @throws NotAOperatorException if the user type check fails
     */
    String getUserType() throws NotAOperatorException;

    /**
     * Withdraws a specified amount from the account.
     *
     * @param wthAmount the amount to withdraw
     * @return the withdrawn amount
     * @throws InvalidAmountException if the amount is not in a valid denomination
     * @throws InsufficientBalanceException if the account balance is insufficient
     * @throws InsufficientMachineBalanceException if the ATM machine does not have enough cash
     */
    double withdrawAmount(double wthAmount)
            throws InvalidAmountException, InsufficientBalanceException, InsufficientMachineBalanceException;

    /**
     * Deposits a specified amount into the account.
     *
     * @param dptAmount the amount to deposit
     * @throws InvalidAmountException if the deposit amount is invalid
     */
    void depositAmount(double dptAmount) throws InvalidAmountException;

    /**
     * Checks the current account balance.
     *
     * @return the account balance
     */
    double checkAccountBalance();

    /**
     * Changes the account PIN number.
     *
     * @param pinNumber the new PIN number
     */
    void changePinNumber(int pinNumber);

    /**
     * Gets the account's PIN number.
     *
     * @return the PIN number
     */
    int getPinNumber();

    /**
     * Gets the account holder's name.
     *
     * @return the user name
     */
    String getUserName();

    /**
     * Decreases the number of allowed PIN attempts after a wrong PIN entry.
     */
    void decreaseChances();

    /**
     * Gets the remaining number of PIN entry chances.
     *
     * @return the number of remaining chances
     */
    int getChances();

    /**
     * Resets the PIN attempt counter to the maximum allowed chances.
     */
    void resetPinChances();

    /**
     * Generates and displays the mini-statement of recent transactions.
     */
    void generateMiniStatement();
}

