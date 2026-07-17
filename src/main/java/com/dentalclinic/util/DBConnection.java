package com.dentalclinic.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Database details
    private static final String URL =
            "jdbc:mysql://localhost:3306/dental_clinic_db";

    private static final String USER = "root";

    private static final String PASSWORD = "";

    // Singleton connection object
    private static Connection connection;

    // Private constructor
    private DBConnection() {
    }

    // Get database connection
    public static Connection getConnection() {

        if (connection == null) {

            try {

                Class.forName("com.mysql.cj.jdbc.Driver");

                connection = DriverManager.getConnection(
                        URL,
                        USER,
                        PASSWORD
                );

                System.out.println("Database Connected Successfully!");

            } catch (ClassNotFoundException | SQLException e) {

                e.printStackTrace();

            }

        }

        return connection;

    }

}