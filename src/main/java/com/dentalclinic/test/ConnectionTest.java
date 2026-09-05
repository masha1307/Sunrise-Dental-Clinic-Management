package com.dentalclinic.test;

import java.sql.Connection;
import java.util.logging.Logger;

import com.dentalclinic.util.DBConnection;

public class ConnectionTest {

    private static final Logger logger = Logger.getLogger(ConnectionTest.class.getName());

    public static void main(String[] args) {

        Connection connection = DBConnection.getConnection();

        if (connection != null) {

            logger.info("Connection Successful!");

        } else {

            logger.severe("Connection Failed!");

        }

    }

}