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


                connection = DriverManager.getConnection(
                        URL,
                        USERNAME,
                        PASSWORD
                );


            } catch (ClassNotFoundException | SQLException e) {

                e.printStackTrace();

            }

        }


        return connection;

    }

}