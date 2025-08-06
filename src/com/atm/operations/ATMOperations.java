package com.atm.operations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.atm.cards.AxisDebitCard;
import com.atm.cards.HDFCDebitCard;
import com.atm.cards.OperatorDebitCard;
import com.atm.cards.SBIDebitCard;
import com.atm.exceptions.IncorrectPinLimitReachedException;
import com.atm.exceptions.InsufficientBalanceException;
import com.atm.exceptions.InsufficientMachineBalanceException;
import com.atm.exceptions.InvalidAmountException;
import com.atm.exceptions.InvalidCardException;
import com.atm.exceptions.InvalidPinException;
import com.atm.exceptions.NotAOperatorException;
import com.atm.interfaces.IATMService;
import com.atm.persistence.DatabaseLogger;

public class ATMOperations {
    public static double ATM_MACHINE_BALANCE = 100000.0;
    public static ArrayList<String> ACTIVITY = new ArrayList<>();
    public static HashMap<Long, IATMService> dataBase = new HashMap<>();
    public static boolean MACHINE_ON = true;
    public static IATMService card;

    public static IATMService validateCard(long cardNumber) throws InvalidCardException {
        if (dataBase.containsKey(cardNumber)) {
            return dataBase.get(cardNumber);
        } else {
            ACTIVITY.add("Accessed by: " + cardNumber + " is Not Compatible");
            throw new InvalidCardException("This is Not A valid Card");
        }
    }

    public static void checkATMMachineActivities() {
        System.out.println("=================== Activities Performed ===================");
        for (String activity : ACTIVITY) {
            System.out.println("==========================================================");
            System.out.println(activity);
            System.out.println("==========================================================");
        }
    }

    public static void resetUserAttempts(IATMService operatorCard) {
        IATMService card = null;
        long number;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your CARD Number:");
        number = scanner.nextLong();
        try {
            card = validateCard(number);
            card.resetPinChances();
            ACTIVITY.add("Accessed By: " + operatorCard.getUserName() + " reset number of chances for user.");
        } catch (InvalidCardException ive) {
            System.out.println(ive.getMessage());
        }
    }

    public static IATMService validateCredentials(long cardNumber, int pinNumber)
            throws InvalidCardException, IncorrectPinLimitReachedException, InvalidPinException {
        if (dataBase.containsKey(cardNumber)) {
            card = dataBase.get(cardNumber);
        } else {
            throw new InvalidCardException("This card is Not A valid Card");
        }
        try {
            if (card.getUserType().equals("operator")) {
                if (card.getPinNumber() != pinNumber) {
                    throw new InvalidPinException("Dear operator, Please enter Correct PIN Number");
                } else {
                    return card;
                }
            }
        } catch (NotAOperatorException noe) {
            noe.printStackTrace();
        }
        if (card.getChances() <= 0) {
            throw new IncorrectPinLimitReachedException(
                    "You have Reached Wrong Limit of PIN Number, which is 3 attempts");
        }
        if (card.getPinNumber() != pinNumber) {
            card.decreaseChances();
            throw new InvalidPinException("You Have Entered A Wrong PIN Number");
        } else {
            return card;
        }
    }

    public static void validateAmount(double amount) throws InsufficientMachineBalanceException {
        if (amount > ATM_MACHINE_BALANCE) {
            throw new InsufficientMachineBalanceException("Insufficient cash in the Machine");
        }
    }

    public static void validateDepositAmount(double amount)
            throws InsufficientMachineBalanceException, InvalidAmountException {
        if (amount % 100 != 0) {
            throw new InvalidAmountException("Please deposit amounts in multiples of 100.");
        }
        if (amount + ATM_MACHINE_BALANCE > 200000.0d) {
            ACTIVITY.add("Unable to deposit cash in the machine...");
            throw new InsufficientMachineBalanceException(
                    "You can't deposit cash as the limit of the machine is reached.");
        }
    }

    public static void operatorMode(IATMService card) {
        Scanner scanner = new Scanner(System.in);
        double amount;
        boolean flag = true;
        while (flag) {
            System.out.println("OPERATOR MODE: Operator Name: " + card.getUserName());
            System.out.println("===================================================");
            System.out.println("0. Switch Off The Machine");
            System.out.println("1. Check ATM Machine Balance");
            System.out.println("2. Deposit Cash In The Machine");
            System.out.println("3. Reset The User PIN Attempts");
            System.out.println("4. Check Activities Performed");
            System.out.println("5. Exit Operator Mode");
            System.out.println("Please Enter Your Choice: ");
            int option = scanner.nextInt();
            switch (option) {
                case 0:
                    MACHINE_ON = false;
                    ACTIVITY.add("Accessed By " + card.getUserName() + " switched off the ATM");
                    flag = false;
                    break;
                case 1:
                    ACTIVITY.add("Accessed By " + card.getUserName() + " checked ATM balance");
                    System.out.println("ATM Balance: " + ATM_MACHINE_BALANCE);
                    break;
                case 2:
                    System.out.println("Enter Amount To Deposit: ");
                    amount = scanner.nextDouble();
                    try {
                        validateDepositAmount(amount);
                        ATM_MACHINE_BALANCE += amount;
                        ACTIVITY.add("Accessed By " + card.getUserName() + " deposited cash into ATM");
                        System.out.println("Cash added successfully!");
                    } catch (InvalidAmountException | InsufficientMachineBalanceException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    resetUserAttempts(card);
                    System.out.println("User PIN attempts reset.");
                    ACTIVITY.add("Accessed By " + card.getUserName() + " reset PIN attempts for a user");
                    break;
                case 4:
                    checkATMMachineActivities();
                    break;
                case 5:
                    flag = false;
                    break;
                default:
                    System.out.println("Invalid Option");
            }
        }
    }

    public static void main(String[] args) throws NotAOperatorException {
        dataBase.put(222222221L, new AxisDebitCard(222222221L, "Yashas", 50000.0, 2222));
        dataBase.put(3333333331L, new SBIDebitCard(3333333331L, "Akshay", 55000.0, 3333));
        dataBase.put(4444444441L, new AxisDebitCard(4444444441L, "Das", 32500.0, 4444));
        dataBase.put(5555555551L, new HDFCDebitCard(5555555551L, "Aravind", 71000.0, 5555));
        dataBase.put(1111111111L, new OperatorDebitCard(1111111111L, 1111, "Operator 1"));

        Scanner scanner = new Scanner(System.in);
        long cardNumber;
        double depositAmount;
        double withdrawAmount;
        int pin;

        while (MACHINE_ON) {
            System.out.println("Please Enter the Debit Card Number:");
            cardNumber = scanner.nextLong();

            try {
                System.out.println("Please Enter PIN Number:");
                pin = scanner.nextInt();
                card = validateCredentials(cardNumber, pin);

                if (card == null) {
                    System.out.println("Card validation failed.");
                    continue;
                }

                ACTIVITY.add("Accessed By: " + card.getUserName() + " Status: Access Approved");

                if (card.getUserType().equals("operator")) {
                    operatorMode(card);
                    continue;
                }

                while (true) {
                    System.out.println("USER MODE: " + card.getUserName());
                    System.out.println("1. Withdraw Amount");
                    System.out.println("2. Deposit Amount");
                    System.out.println("3. Check Balance");
                    System.out.println("4. Change PIN");
                    System.out.println("5. Mini Statement");
                    System.out.println("Enter Your Choice:");
                    int option = scanner.nextInt();

                    try {
                        switch (option) {
                            case 1:
                                System.out.println("Please Enter The Amount to Withdraw: ");
                                withdrawAmount = scanner.nextDouble();
                                validateAmount(withdrawAmount);
                                card.withdrawAmount(withdrawAmount);
                                ATM_MACHINE_BALANCE -= withdrawAmount;
                                ACTIVITY.add(card.getUserName() + " withdrew " + withdrawAmount);

                                DatabaseLogger.log(((AxisDebitCard) card).getCardNumber(),
                                        card.getUserName() + " withdrew " + withdrawAmount);
                                break;

                            case 2:
                                System.out.println("Please Enter The Amount to Deposit: ");
                                depositAmount = scanner.nextDouble();
                                validateDepositAmount(depositAmount);
                                ATM_MACHINE_BALANCE += depositAmount;
                                card.depositAmount(depositAmount);
                                ACTIVITY.add(card.getUserName() + " deposited " + depositAmount);

                                DatabaseLogger.log(((AxisDebitCard) card).getCardNumber(),
                                        card.getUserName() + " deposited " + depositAmount);
                                break;

                            case 3:
                                double balance = card.checkAccountBalance();
                                System.out.println("Your Account Balance is: " + balance);
                                ACTIVITY.add(card.getUserName() + " checked balance: " + balance);

                                DatabaseLogger.log(((AxisDebitCard) card).getCardNumber(),
                                        card.getUserName() + " checked balance: " + balance);
                                break;

                            case 4:
                                System.out.println("Enter A New PIN:");
                                pin = scanner.nextInt();
                                card.changePinNumber(pin);
                                ACTIVITY.add(card.getUserName() + " changed PIN");

                                DatabaseLogger.log(((AxisDebitCard) card).getCardNumber(),
                                        card.getUserName() + " changed PIN");
                                break;

                            case 5:
                                ACTIVITY.add(card.getUserName() + " requested mini statement");

                                DatabaseLogger.log(((AxisDebitCard) card).getCardNumber(),
                                        card.getUserName() + " requested mini statement");

                                card.generateMiniStatement();
                                break;

                            default:
                                System.out.println("Invalid Option");
                                break;
                        }
                        System.out.println("Do You Want To Continue? (Y/N):");
                        String nextOption = scanner.next();
                        if (nextOption.equalsIgnoreCase("N")) {
                            break;
                        }
                    } catch (InvalidAmountException | InsufficientBalanceException
                             | InsufficientMachineBalanceException e) {
                        System.out.println(e.getMessage());
                    }
                }
            } catch (InvalidPinException | InvalidCardException | IncorrectPinLimitReachedException e) {
                ACTIVITY.add("Accessed By: " + (card != null ? card.getUserName() : cardNumber) + " Status: Access Denied");
                System.out.println(e.getMessage());
            }
        }
        System.out.println("===== Thanks For Using ICCI ATM Machine =====");
    }
}



