package com.atm.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides a reusable connection to the MySQL database.
 */
public class DatabaseConnection {

    // Updated JDBC URL with SSL & timezone settings for MySQL 8+
    private static final String URL =
            "jdbc:mysql://localhost:3306/atm_system?useSSL=false&serverTimezone=UTC";

    private static final String USER = "root"; // your MySQL username
    private static final String PASSWORD = "harr_ssshhhh_222"; // your MySQL password

    public static Connection getConnection() throws SQLException {
        try {
            // Force load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found in classpath.", e);
        }

        // Return connection to the database
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}



