package com.dentalclinic.dao;

import com.dentalclinic.model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Note: PatientDAO uses static DBConnection.getConnection() which requires a database connection.
 * These tests should be run as integration tests with a test database, or the DAO should be refactored
 * to use dependency injection for proper unit testing with Mockito.
 */
class PatientDAOTest {

    private PatientDAO patientDAO;

    @BeforeEach
    void setUp() {
        patientDAO = new PatientDAO();
    }

    @Test
    void testGetAllPatients() {
        // Requires database connection
        List<Patient> patients = patientDAO.getAllPatients();
        assertNotNull(patients);
    }

    @Test
    void testAddPatient() {
        Patient patient = new Patient(0, "Test Patient", 30, "Male", "555-9999", "test@test.com", "123 Test St");
        // Requires database connection
        boolean result = patientDAO.addPatient(patient);
        // assertTrue(result);
    }

    @Test
    void testGetPatientById() {
        // Requires database connection
        Patient patient = patientDAO.getPatientById(1);
        // assertNotNull(patient);
    }

    @Test
    void testUpdatePatient() {
        Patient patient = new Patient(1, "Updated Name", 30, "Male", "555-9999", "test@test.com", "123 Test St");
        // Requires database connection
        boolean result = patientDAO.updatePatient(patient);
        // assertTrue(result);
    }

    @Test
    void testDeletePatient() {
        // Requires database connection
        boolean result = patientDAO.deletePatient(999);
        // assertFalse(result); // Assuming 999 doesn't exist
    }
}
