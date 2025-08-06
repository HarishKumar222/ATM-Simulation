package com.atm.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Logs ATM transactions into the MySQL transactions table.
 */
public class DatabaseLogger {

    /**
     * Adds a transaction record to the database.
     *
     * @param cardNumber  The card number involved in the transaction.
     * @param description Description of the transaction.
     */
    public static void log(long cardNumber, String description) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO transactions (card_number, description) VALUES (?, ?)")) {

            stmt.setLong(1, cardNumber);
            stmt.setString(2, description);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error logging transaction: " + e.getMessage());
        }
    }
    public static void printLastTransactions(long cardNumber, int limit) {
        String query = "SELECT description, timestamp FROM transactions " +
                "WHERE card_number = ? " +
                "AND (description LIKE '%withdrew%' " +
                "     OR description LIKE '%deposited%' " +
                "     OR description LIKE '%balance%' " +
                "     OR description LIKE '%Changed PIN%' ) " +
                "ORDER BY timestamp DESC LIMIT ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, cardNumber);
            stmt.setInt(2, limit);

            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("===== Last " + limit + " Transactions =====");
                if (!rs.isBeforeFirst()) {
                    System.out.println("No transactions found.");
                    return;
                }
                while (rs.next()) {
                    System.out.println(rs.getTimestamp("timestamp") + " - " + rs.getString("description"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching mini statement: " + e.getMessage());
        }
    }


}


