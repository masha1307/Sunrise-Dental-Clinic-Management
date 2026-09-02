package com.dentalclinic.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

    @Test
    void testDefaultConstructor() {
        Admin admin = new Admin();
        assertEquals(0, admin.getAdminId());
        assertNull(admin.getUsername());
        assertNull(admin.getPassword());
        assertNull(admin.getName());
        assertNull(admin.getEmail());
    }

    @Test
    void testParameterizedConstructor() {
        Admin admin = new Admin(1, "admin", "admin123", "System Administrator", "admin@dentalclinic.com");
        assertEquals(1, admin.getAdminId());
        assertEquals("admin", admin.getUsername());
        assertEquals("admin123", admin.getPassword());
        assertEquals("System Administrator", admin.getName());
        assertEquals("admin@dentalclinic.com", admin.getEmail());
    }

    @Test
    void testSettersAndGetters() {
        Admin admin = new Admin();
        admin.setAdminId(2);
        admin.setUsername("testadmin");
        admin.setPassword("testpass");
        admin.setName("Test Admin");
        admin.setEmail("test@dentalclinic.com");

        assertEquals(2, admin.getAdminId());
        assertEquals("testadmin", admin.getUsername());
        assertEquals("testpass", admin.getPassword());
        assertEquals("Test Admin", admin.getName());
        assertEquals("test@dentalclinic.com", admin.getEmail());
    }

    @Test
    void testSetAdminId() {
        Admin admin = new Admin();
        admin.setAdminId(100);
        assertEquals(100, admin.getAdminId());
    }

    @Test
    void testSetUsername() {
        Admin admin = new Admin();
        admin.setUsername("newuser");
        assertEquals("newuser", admin.getUsername());
    }

    @Test
    void testSetPassword() {
        Admin admin = new Admin();
        admin.setPassword("newpass");
        assertEquals("newpass", admin.getPassword());
    }

    @Test
    void testSetName() {
        Admin admin = new Admin();
        admin.setName("New Name");
        assertEquals("New Name", admin.getName());
    }

    @Test
    void testSetEmail() {
        Admin admin = new Admin();
        admin.setEmail("newemail@test.com");
        assertEquals("newemail@test.com", admin.getEmail());
    }
}
