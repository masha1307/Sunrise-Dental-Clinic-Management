package com.dentalclinic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.dentalclinic.model.Patient;
import com.dentalclinic.util.DBConnection;

public class PatientDAO {

    public boolean addPatient(Patient patient) {
        String sql = "INSERT INTO patients (patient_name, age, gender, contact_number, email, address) VALUES (?, ?, ?, ?, ?, ?)";
        
        try {
            Connection connection = DBConnection.getConnection();
            
            if (connection == null) {
                System.err.println("ERROR: Database connection is null");
                return false;
            }

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            statement.setString(1, patient.getPatientName());
            statement.setInt(2, patient.getAge());
            statement.setString(3, patient.getGender());
            statement.setString(4, patient.getContactNumber());
            statement.setString(5, patient.getEmail());
            statement.setString(6, patient.getAddress());

            int rowsInserted = statement.executeUpdate();
            
            if (rowsInserted > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    patient.setPatientId(generatedKeys.getInt(1));
                    System.out.println("Patient added successfully with ID: " + patient.getPatientId());
                }
            }
            
            return rowsInserted > 0;

        } catch (Exception e) {
            System.err.println("ERROR adding patient: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePatient(Patient patient) {
        String sql = "UPDATE patients SET patient_name = ?, age = ?, gender = ?, contact_number = ?, email = ?, address = ? WHERE patient_id = ?";
        try {
            Connection connection = DBConnection.getConnection();
            
            if (connection == null) {
                System.err.println("ERROR: Database connection is null");
                return false;
            }

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, patient.getPatientName());
            statement.setInt(2, patient.getAge());
            statement.setString(3, patient.getGender());
            statement.setString(4, patient.getContactNumber());
            statement.setString(5, patient.getEmail());
            statement.setString(6, patient.getAddress());
            statement.setInt(7, patient.getPatientId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            System.err.println("ERROR updating patient: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePatient(int patientId) {
        String sql = "DELETE FROM patients WHERE patient_id = ?";
        try {
            Connection connection = DBConnection.getConnection();
            
            if (connection == null) {
                System.err.println("ERROR: Database connection is null");
                return false;
            }

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, patientId);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (Exception e) {
            System.err.println("ERROR deleting patient: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Patient getPatientById(int patientId) {
        String sql = "SELECT * FROM patients WHERE patient_id = ?";
        
        try {
            Connection connection = DBConnection.getConnection();
            
            if (connection == null) {
                System.err.println("ERROR: Database connection is null");
                return null;
            }

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, patientId);
            
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                Patient patient = new Patient();
                patient.setPatientId(resultSet.getInt("patient_id"));
                patient.setPatientName(resultSet.getString("patient_name"));
                patient.setAge(resultSet.getInt("age"));
                patient.setGender(resultSet.getString("gender"));
                patient.setContactNumber(resultSet.getString("contact_number"));
                patient.setEmail(resultSet.getString("email"));
                patient.setAddress(resultSet.getString("address"));
                return patient;
            }
            
        } catch (Exception e) {
            System.err.println("ERROR getting patient: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }

    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        
        try {
            Connection connection = DBConnection.getConnection();
            
            if (connection == null) {
                System.err.println("ERROR: Database connection is null");
                return patients;
            }

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Patient patient = new Patient();
                patient.setPatientId(resultSet.getInt("patient_id"));
                patient.setPatientName(resultSet.getString("patient_name"));
                patient.setAge(resultSet.getInt("age"));
                patient.setGender(resultSet.getString("gender"));
                patient.setContactNumber(resultSet.getString("contact_number"));
                patient.setEmail(resultSet.getString("email"));
                patient.setAddress(resultSet.getString("address"));
                patients.add(patient);
            }
            
            System.out.println("Retrieved " + patients.size() + " patients from database");
            
        } catch (Exception e) {
            System.err.println("ERROR retrieving patients: " + e.getMessage());
            e.printStackTrace();
        }
        
        return patients;
    }
}