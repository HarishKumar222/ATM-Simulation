package com.atm.cards;

import java.util.ArrayList;
import java.util.Collections;

import com.atm.exceptions.InsufficientBalanceException;
import com.atm.exceptions.InvalidAmountException;
import com.atm.exceptions.NotAOperatorException;
import com.atm.exceptions.InsufficientMachineBalanceException;
import com.atm.interfaces.IATMService;

public class SBIDebitCard implements IATMService {
    private String name;
    private long debitCardNumber;
    private double accountBalance;
    private int pinNumber;
    private ArrayList<String> statement;
    private final String type = "user";
    private int chances;

    public SBIDebitCard(long debitCardNumber, String name, double accountBalance, int pinNumber) {
        this.chances = 3;
        this.debitCardNumber = debitCardNumber;
        this.name = name;
        this.accountBalance = accountBalance;
        this.pinNumber = pinNumber;
        this.statement = new ArrayList<>();
    }

    @Override
    public String getUserType() throws NotAOperatorException {
        return type;
    }

    @Override
    public double withdrawAmount(double wthAmount)
            throws InvalidAmountException, InsufficientBalanceException, InsufficientMachineBalanceException {
        if (wthAmount <= 0) {
            throw new InvalidAmountException("You cannot withdraw zero or negative amounts. Please enter a valid amount.");
        } else if (wthAmount % 100 != 0) {
            throw new InvalidAmountException("Please withdraw in multiples of 100.");
        } else if (wthAmount < 500) {
            throw new InvalidAmountException("Please withdraw at least 500.");
        } else if (wthAmount > accountBalance) {
            throw new InsufficientBalanceException(
                    "You don't have sufficient balance to withdraw. Please check your account balance.");
        } else {
            accountBalance -= wthAmount;
            statement.add("Debited: " + wthAmount);
            return wthAmount;
        }
    }

    @Override
    public void depositAmount(double dptAmount) throws InvalidAmountException {
        if (dptAmount <= 0 || dptAmount % 100 != 0) {
            throw new InvalidAmountException("Please deposit in multiples of 100.");
        } else {
            accountBalance += dptAmount;
            statement.add("Credited: " + dptAmount);
        }
    }

    @Override
    public double checkAccountBalance() {
        return accountBalance;
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
        --chances;
    }

    @Override
    public int getChances() {
        return chances;
    }

    @Override
    public void resetPinChances() {
        chances = 3;
    }

    @Override
    public void generateMiniStatement() {
        System.out.println("---------Last 5 Transactions----------");
        com.atm.persistence.DatabaseLogger.printLastTransactions(getCardNumber(), 5);
    }
    public long getCardNumber() {
        return debitCardNumber; // or 'id' in OperatorDebitCard
    }
}


