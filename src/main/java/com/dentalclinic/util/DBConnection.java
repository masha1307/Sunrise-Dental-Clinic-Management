package com.dentalclinic.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    private static Connection connection;
    private static boolean initialized = false;

    // Database details
    private static final String URL =
            "jdbc:mysql://localhost:3306/dental_clinic_db";

    private static final String USERNAME =
            "root";

    private static final String PASSWORD =
            "Gangu@123";

    // Private constructor prevents object creation
    private DBConnection() {
    }

    // Singleton method with auto-reconnect & table initialization
    public static synchronized Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("Attempting to connect to database: " + URL);

                connection = DriverManager.getConnection(
                        URL,
                        USERNAME,
                        PASSWORD
                );

                System.out.println("Database connection established successfully");
                initializeDatabase(connection);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: MySQL JDBC Driver not found");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("ERROR: Failed to connect to database");
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
        }

        return connection;
    }

    private static void initializeDatabase(Connection conn) {
        if (initialized || conn == null) return;

        try (Statement stmt = conn.createStatement()) {
            // 1. Create admins table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS admins (" +
                    "admin_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "username VARCHAR(50) NOT NULL UNIQUE, " +
                    "password VARCHAR(255) NOT NULL, " +
                    "name VARCHAR(100), " +
                    "email VARCHAR(100)" +
                    ")");

            // 2. Create receptionists table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS receptionists (" +
                    "receptionist_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "username VARCHAR(50) NOT NULL UNIQUE, " +
                    "password VARCHAR(255) NOT NULL, " +
                    "name VARCHAR(100), " +
                    "contact_number VARCHAR(20), " +
                    "email VARCHAR(100)" +
                    ")");

            // 3. Ensure default admin exists
            stmt.executeUpdate("INSERT INTO admins (username, password, name, email) " +
                    "SELECT 'admin', 'admin123', 'System Administrator', 'admin@dentalclinic.com' " +
                    "WHERE NOT EXISTS (SELECT 1 FROM admins WHERE username = 'admin')");

            // 4. Ensure default receptionist exists
            stmt.executeUpdate("INSERT INTO receptionists (username, password, name, contact_number, email) " +
                    "SELECT 'receptionist', 'recep123', 'Emily Davis', '555-0201', 'emily@dentalclinic.com' " +
                    "WHERE NOT EXISTS (SELECT 1 FROM receptionists WHERE username = 'receptionist')");

            initialized = true;
            System.out.println("Database tables initialized successfully: admins and receptionists verified.");
        } catch (SQLException e) {
            System.err.println("Note on auto-init database: " + e.getMessage());
        }
    }
}