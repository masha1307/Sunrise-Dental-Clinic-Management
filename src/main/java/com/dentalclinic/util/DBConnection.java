package com.dentalclinic.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection connection;


    // Database details
    private static final String URL =
            "jdbc:mysql://localhost:3306/dental_clinic_db";

    private static final String USERNAME =
            "root";

    private static final String PASSWORD =
            "";


    // Private constructor prevents object creation
    private DBConnection() {

    }


    // Singleton method
    public static Connection getConnection() {


        if (connection == null) {

            try {

                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("Attempting to connect to database: " + URL);


                connection = DriverManager.getConnection(
                        URL,
                        USERNAME,
                        PASSWORD
                );
                
                System.out.println("Database connection established successfully");


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

        } else {
            System.out.println("Using existing database connection");
        }


        return connection;

    }

}