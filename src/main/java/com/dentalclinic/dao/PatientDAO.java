package com.dentalclinic.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.dentalclinic.model.Patient;
import com.dentalclinic.util.DBConnection;

public class PatientDAO {

    public boolean addPatient(Patient patient) {
        return false;
    }

    public boolean updatePatient(Patient patient) {
        return false;
    }

    public boolean deletePatient(int patientId) {
        return false;
    }

    public Patient getPatientById(int patientId) {
        return null;
    }

    public List<Patient> getAllPatients() {
        return new ArrayList<>();
    }
}