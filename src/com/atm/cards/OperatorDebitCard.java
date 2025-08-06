package com.atm.cards;

import com.atm.exceptions.InsufficientBalanceException;
import com.atm.exceptions.InvalidAmountException;
import com.atm.exceptions.NotAOperatorException;
import com.atm.exceptions.InsufficientMachineBalanceException;
import com.atm.interfaces.IATMService;

public class OperatorDebitCard implements IATMService {

    private int pinNumber;
    private long id;
    private String name;
    private final String type = "operator";

    public OperatorDebitCard(long idn, int pin, String name) {
        this.id = idn;
        this.pinNumber = pin;
        this.name = name;
    }

    @Override
    public String getUserType() throws NotAOperatorException {
        return type;
    }

    @Override
    public double withdrawAmount(double wthAmount)
            throws InvalidAmountException, InsufficientBalanceException, InsufficientMachineBalanceException {
        // Operators don’t perform withdrawals from their own accounts in this simulation.
        return 0;
    }

    @Override
    public void depositAmount(double dptAmount) throws InvalidAmountException {
        // Operators deposit cash into the ATM via operator mode, not here.
    }

    @Override
    public double checkAccountBalance() {
        // Not applicable for operators in this simulation.
        return 0;
    }

    @Override
    public void changePinNumber(int pinNumber) {
        this.pinNumber = pinNumber;
    }

    @Override
    public int getPinNumber() {
        return pinNumber;
    }

    @Override
    public String getUserName() {
        return name;
    }

    @Override
    public void decreaseChances() {
        // Not used for operators.
    }

    @Override
    public int getChances() {
        // Not used for operators.
        return 0;
    }

    @Override
    public void resetPinChances() {
        // Not used for operators.
    }

    @Override
    public void generateMiniStatement() {
        // Not applicable for operators.
    }
}

