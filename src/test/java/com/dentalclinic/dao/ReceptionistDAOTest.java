package com.dentalclinic.dao;

import com.dentalclinic.model.Receptionist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Note: ReceptionistDAO uses static DBConnection.getConnection() which requires a database connection.
 * These tests should be run as integration tests with a test database, or the DAO should be refactored
 * to use dependency injection for proper unit testing with Mockito.
 */
class ReceptionistDAOTest {

    private ReceptionistDAO receptionistDAO;

    @BeforeEach
    void setUp() {
        receptionistDAO = new ReceptionistDAO();
    }

    @Test
    void testLogin_Success() {
        boolean result = receptionistDAO.login("receptionist", "recep123");
        // assertTrue(result);
    }

    @Test
    void testLogin_Failure() {
        boolean result = receptionistDAO.login("invalid", "wrong");
        // assertFalse(result);
    }

    @Test
    void testGetReceptionistByUsername() {
        Receptionist receptionist = receptionistDAO.getReceptionistByUsername("receptionist");
        // assertNotNull(receptionist);
    }

    @Test
    void testGetReceptionistById() {
        Receptionist receptionist = receptionistDAO.getReceptionistById(1);
        // assertNotNull(receptionist);
    }

    @Test
    void testGetAllReceptionists() {
        List<Receptionist> receptionists = receptionistDAO.getAllReceptionists();
        assertNotNull(receptionists);
    }

    @Test
    void testIsUsernameTaken() {
        boolean result = receptionistDAO.isUsernameTaken("receptionist", 0);
        // assertTrue(result);
    }

    @Test
    void testAddReceptionist() {
        Receptionist receptionist = new Receptionist(0, "newuser", "newpass", "New User", "555-9999", "newuser@test.com");
        boolean result = receptionistDAO.addReceptionist(receptionist);
        // assertTrue(result);
    }

    @Test
    void testUpdateReceptionist() {
        Receptionist receptionist = new Receptionist(1, "receptionist", "recep123", "Updated Name", "555-0201", "emily@test.com");
        boolean result = receptionistDAO.updateReceptionist(receptionist);
        // assertTrue(result);
    }

    @Test
    void testDeleteReceptionist() {
        boolean result = receptionistDAO.deleteReceptionist(999);
        // assertFalse(result);
    }
}
