package com.atm.persistence;

import com.atm.cards.AxisDebitCard;
import com.atm.cards.HDFCDebitCard;
import com.atm.cards.SBIDebitCard;
import com.atm.cards.OperatorDebitCard;
import com.atm.interfaces.IATMService;

import java.sql.*;
import java.util.HashMap;

/**
 * Handles loading and saving account data from MySQL.
 */
public class DatabaseDataStore {

    /**
     * Loads all accounts from the database into a HashMap.
     */
    public static HashMap<Long, IATMService> loadAccounts() {
        HashMap<Long, IATMService> accounts = new HashMap<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM accounts")) {

            while (rs.next()) {
                long cardNumber = rs.getLong("card_number");
                String name = rs.getString("name");
                double balance = rs.getDouble("balance");
                int pin = rs.getInt("pin");
                String type = rs.getString("type").toLowerCase();

                switch (type) {
                    case "axis" -> accounts.put(cardNumber, new AxisDebitCard(cardNumber, name, balance, pin));
                    case "hdfc" -> accounts.put(cardNumber, new HDFCDebitCard(cardNumber, name, balance, pin));
                    case "sbi" -> accounts.put(cardNumber, new SBIDebitCard(cardNumber, name, balance, pin));
                    case "operator" -> accounts.put(cardNumber, new OperatorDebitCard(cardNumber, pin, name));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error loading accounts from database: " + e.getMessage());
        }
        return accounts;
    }

    /**
     * Updates account balance and PIN in the database.
     */
    public static void updateAccount(long cardNumber, double balance, int pin) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE accounts SET balance = ?, pin = ? WHERE card_number = ?")) {

            stmt.setDouble(1, balance);
            stmt.setInt(2, pin);
            stmt.setLong(3, cardNumber);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error updating account: " + e.getMessage());
        }
    }
}


