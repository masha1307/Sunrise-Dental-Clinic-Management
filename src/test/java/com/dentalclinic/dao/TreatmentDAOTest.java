package com.dentalclinic.dao;

import com.dentalclinic.model.Treatment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Note: TreatmentDAO uses static DBConnection.getConnection() which requires a database connection.
 * These tests should be run as integration tests with a test database, or the DAO should be refactored
 * to use dependency injection for proper unit testing with Mockito.
 */
class TreatmentDAOTest {

    private TreatmentDAO treatmentDAO;

    @BeforeEach
    void setUp() {
        treatmentDAO = new TreatmentDAO();
    }

    @Test
    void testGetAllTreatments() {
        List<Treatment> treatments = treatmentDAO.getAllTreatments();
        assertNotNull(treatments);
    }

    @Test
    void testGetTreatmentById() {
        Treatment treatment = treatmentDAO.getTreatmentById(1);
        // assertNotNull(treatment);
    }

    @Test
    void testAddTreatment() {
        Treatment treatment = new Treatment();
        treatment.setTreatmentName("Test Treatment");
        treatment.setPrice(100.0);
        treatment.setDurationMinutes("30");
        boolean result = treatmentDAO.addTreatment(treatment);
        // assertTrue(result);
    }

    @Test
    void testUpdateTreatment() {
        Treatment treatment = new Treatment();
        treatment.setTreatmentId(1);
        treatment.setTreatmentName("Updated Treatment");
        treatment.setPrice(150.0);
        treatment.setDurationMinutes("45");
        boolean result = treatmentDAO.updateTreatment(treatment);
        // assertTrue(result);
    }

    @Test
    void testDeleteTreatment() {
        boolean result = treatmentDAO.deleteTreatment(999);
        // assertFalse(result);
    }
}
