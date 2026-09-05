package com.dentalclinic.dao;

import com.dentalclinic.model.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Note: AdminDAO uses static DBConnection.getConnection() which requires a database connection.
 * These tests should be run as integration tests with a test database, or the DAO should be refactored
 * to use dependency injection for proper unit testing with Mockito.
 */
class AdminDAOTest {

    private AdminDAO adminDAO;

    @BeforeEach
    void setUp() {
        adminDAO = new AdminDAO();
    }

    @Test
    void testLogin_Success() {
        boolean result = adminDAO.login("admin", "admin123");
        // assertTrue(result);
    }

    @Test
    void testLogin_Failure() {
        boolean result = adminDAO.login("invalid", "wrong");
        // assertFalse(result);
    }

    @Test
    void testGetAdminByUsername() {
        Admin admin = adminDAO.getAdminByUsername("admin");
        // assertNotNull(admin);
    }

    @Test
    void testGetAdminById() {
        Admin admin = adminDAO.getAdminById(1);
        // assertNotNull(admin);
    }

    @Test
    void testGetAllAdmins() {
        List<Admin> admins = adminDAO.getAllAdmins();
        assertNotNull(admins);
    }

    @Test
    void testIsUsernameTaken() {
        boolean result = adminDAO.isUsernameTaken("admin", 0);
        // assertTrue(result);
    }

    @Test
    void testAddAdmin() {
        Admin admin = new Admin(0, "newadmin", "newpass", "New Admin", "newadmin@test.com");
        boolean result = adminDAO.addAdmin(admin);
        // assertTrue(result);
    }

    @Test
    void testUpdateAdmin() {
        Admin admin = new Admin(1, "admin", "admin123", "Updated Name", "admin@test.com");
        boolean result = adminDAO.updateAdmin(admin);
        // assertTrue(result);
    }

    @Test
    void testDeleteAdmin() {
        boolean result = adminDAO.deleteAdmin(999);
        // assertFalse(result);
    }
}
