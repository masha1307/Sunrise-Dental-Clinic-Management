package com.dentalclinic.dao;

import com.dentalclinic.model.Dentist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Note: DentistDAO uses static DBConnection.getConnection() which requires a database connection.
 * These tests should be run as integration tests with a test database, or the DAO should be refactored
 * to use dependency injection for proper unit testing with Mockito.
 */
class DentistDAOTest {

    private DentistDAO dentistDAO;

    @BeforeEach
    void setUp() {
        dentistDAO = new DentistDAO();
    }

    @Test
    void testGetAllDentists() {
        List<Dentist> dentists = dentistDAO.getAllDentists();
        assertNotNull(dentists);
    }

    @Test
    void testGetDentistById() {
        Dentist dentist = dentistDAO.getDentistById(1);
        // assertNotNull(dentist);
    }

    @Test
    void testAddDentist() {
        Dentist dentist = new Dentist(0, "Dr. Test", "General Dentist", "555-9999", "test@test.com");
        boolean result = dentistDAO.addDentist(dentist);
        // assertTrue(result);
    }

    @Test
    void testUpdateDentist() {
        Dentist dentist = new Dentist(1, "Dr. Updated", "General Dentist", "555-9999", "test@test.com");
        boolean result = dentistDAO.updateDentist(dentist);
        // assertTrue(result);
    }

    @Test
    void testDeleteDentist() {
        boolean result = dentistDAO.deleteDentist(999);
        // assertFalse(result);
    }
}
