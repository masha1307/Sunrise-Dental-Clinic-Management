package com.dentalclinic.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testDefaultConstructor() {
        User user = new User();
        assertEquals(0, user.getUserId());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
        assertNull(user.getRole());
    }

    @Test
    void testParameterizedConstructor() {
        User user = new User(1, "admin", "admin123", "admin");
        assertEquals(1, user.getUserId());
        assertEquals("admin", user.getUsername());
        assertEquals("admin123", user.getPassword());
        assertEquals("admin", user.getRole());
    }

    @Test
    void testSettersAndGetters() {
        User user = new User();
        user.setUserId(2);
        user.setUsername("receptionist");
        user.setPassword("recep123");
        user.setRole("receptionist");

        assertEquals(2, user.getUserId());
        assertEquals("receptionist", user.getUsername());
        assertEquals("recep123", user.getPassword());
        assertEquals("receptionist", user.getRole());
    }

    @Test
    void testSetUserId() {
        User user = new User();
        user.setUserId(100);
        assertEquals(100, user.getUserId());
    }

    @Test
    void testSetUsername() {
        User user = new User();
        user.setUsername("testuser");
        assertEquals("testuser", user.getUsername());
    }

    @Test
    void testSetPassword() {
        User user = new User();
        user.setPassword("testpass");
        assertEquals("testpass", user.getPassword());
    }

    @Test
    void testSetRole() {
        User user = new User();
        user.setRole("admin");
        assertEquals("admin", user.getRole());
    }

    @Test
    void testRoleAdmin() {
        User user = new User(1, "admin", "admin123", "admin");
        assertEquals("admin", user.getRole());
    }

    @Test
    void testRoleReceptionist() {
        User user = new User(2, "receptionist", "recep123", "receptionist");
        assertEquals("receptionist", user.getRole());
    }
}
