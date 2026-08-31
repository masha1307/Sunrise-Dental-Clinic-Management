package com.dentalclinic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.dentalclinic.model.Dentist;
import com.dentalclinic.util.DBConnection;

public class DentistDAO {

    // Get all dentists
    public List<Dentist> getAllDentists() {

        List<Dentist> dentists = new ArrayList<>();

        String sql = "SELECT * FROM dentists";

        try {

            Connection connection = DBConnection.getConnection();
            
            if (connection == null) {
                System.err.println("ERROR: Database connection is null");
                return dentists;
            }

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Dentist dentist = new Dentist();

                dentist.setDentistId(
                        resultSet.getInt("dentist_id")
                );

                dentist.setDentistName(
                        resultSet.getString("dentist_name")
                );

                dentist.setSpecialization(
                        resultSet.getString("specialization")
                );

                dentist.setContactNumber(
                        resultSet.getString("contact_number")
                );

                dentist.setEmail(
                        resultSet.getString("email")
                );

                dentists.add(dentist);
            }
            
            System.out.println("Retrieved " + dentists.size() + " dentists from database");

        } catch (Exception e) {

            System.err.println("ERROR retrieving dentists: " + e.getMessage());
            e.printStackTrace();

        }

        return dentists;
    }


    // Get dentist by ID
    public Dentist getDentistById(int dentistId) {

        Dentist dentist = null;

        String sql = "SELECT * FROM dentists WHERE dentist_id = ?";

        try {

            Connection connection = DBConnection.getConnection();

            if (connection == null) {
                System.err.println("ERROR: Database connection is null");
                return null;
            }

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, dentistId);

            ResultSet resultSet = statement.executeQuery();


            if (resultSet.next()) {

                dentist = new Dentist();

                dentist.setDentistId(
                        resultSet.getInt("dentist_id")
                );

                dentist.setDentistName(
                        resultSet.getString("dentist_name")
                );

                dentist.setSpecialization(
                        resultSet.getString("specialization")
                );

                dentist.setContactNumber(
                        resultSet.getString("contact_number")
                );

                dentist.setEmail(
                        resultSet.getString("email")
                );

                System.out.println("Retrieved dentist with id: " + dentistId);

            } else {
                System.out.println("No dentist found with id: " + dentistId);
            }


        } catch (Exception e) {

            System.err.println("ERROR retrieving dentist by id: " + e.getMessage());
            e.printStackTrace();

        }

        return dentist;
    }

    // Add dentist
    public boolean addDentist(Dentist dentist) {
        String sql = "INSERT INTO dentists (dentist_name, specialization, contact_number, email) VALUES (?, ?, ?, ?)";
        
        try {
            Connection connection = DBConnection.getConnection();
            
            if (connection == null) {
                System.err.println("ERROR: Database connection is null");
                return false;
            }

            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setString(1, dentist.getDentistName());
            statement.setString(2, dentist.getSpecialization());
            statement.setString(3, dentist.getContactNumber());
            statement.setString(4, dentist.getEmail());

            int rowsInserted = statement.executeUpdate();
            
            System.out.println("Dentist added successfully, rows affected: " + rowsInserted);
            
            return rowsInserted > 0;

        } catch (Exception e) {
            System.err.println("ERROR adding dentist: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Update dentist
    public boolean updateDentist(Dentist dentist) {
        String sql = "UPDATE dentists SET dentist_name = ?, specialization = ?, contact_number = ?, email = ? WHERE dentist_id = ?";

        try {
            Connection connection = DBConnection.getConnection();

            if (connection == null) {
                System.err.println("ERROR: Database connection is null");
                return false;
            }

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, dentist.getDentistName());
            statement.setString(2, dentist.getSpecialization());
            statement.setString(3, dentist.getContactNumber());
            statement.setString(4, dentist.getEmail());
            statement.setInt(5, dentist.getDentistId());

            int rowsUpdated = statement.executeUpdate();

            System.out.println("Dentist updated, rows affected: " + rowsUpdated);

            return rowsUpdated > 0;

        } catch (Exception e) {
            System.err.println("ERROR updating dentist: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Delete dentist
    public boolean deleteDentist(int dentistId) {
        String sql = "DELETE FROM dentists WHERE dentist_id = ?";

        try {
            Connection connection = DBConnection.getConnection();

            if (connection == null) {
                System.err.println("ERROR: Database connection is null");
                return false;
            }

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, dentistId);

            int rowsDeleted = statement.executeUpdate();

            System.out.println("Dentist deleted, rows affected: " + rowsDeleted);

            return rowsDeleted > 0;

        } catch (Exception e) {
            System.err.println("ERROR deleting dentist: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}