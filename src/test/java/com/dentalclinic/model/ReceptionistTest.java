package com.dentalclinic.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReceptionistTest {

    @Test
    void testDefaultConstructor() {
        Receptionist receptionist = new Receptionist();
        assertEquals(0, receptionist.getReceptionistId());
        assertNull(receptionist.getUsername());
        assertNull(receptionist.getPassword());
        assertNull(receptionist.getName());
        assertNull(receptionist.getContactNumber());
        assertNull(receptionist.getEmail());
    }

    @Test
    void testParameterizedConstructor() {
        Receptionist receptionist = new Receptionist(1, "receptionist", "recep123", "Emily Davis", "555-0201", "emily@dentalclinic.com");
        assertEquals(1, receptionist.getReceptionistId());
        assertEquals("receptionist", receptionist.getUsername());
        assertEquals("recep123", receptionist.getPassword());
        assertEquals("Emily Davis", receptionist.getName());
        assertEquals("555-0201", receptionist.getContactNumber());
        assertEquals("emily@dentalclinic.com", receptionist.getEmail());
    }

    @Test
    void testSettersAndGetters() {
        Receptionist receptionist = new Receptionist();
        receptionist.setReceptionistId(2);
        receptionist.setUsername("testuser");
        receptionist.setPassword("testpass");
        receptionist.setName("Test User");
        receptionist.setContactNumber("555-0202");
        receptionist.setEmail("test@dentalclinic.com");

        assertEquals(2, receptionist.getReceptionistId());
        assertEquals("testuser", receptionist.getUsername());
        assertEquals("testpass", receptionist.getPassword());
        assertEquals("Test User", receptionist.getName());
        assertEquals("555-0202", receptionist.getContactNumber());
        assertEquals("test@dentalclinic.com", receptionist.getEmail());
    }

    @Test
    void testSetReceptionistId() {
        Receptionist receptionist = new Receptionist();
        receptionist.setReceptionistId(100);
        assertEquals(100, receptionist.getReceptionistId());
    }

    @Test
    void testSetUsername() {
        Receptionist receptionist = new Receptionist();
        receptionist.setUsername("newuser");
        assertEquals("newuser", receptionist.getUsername());
    }

    @Test
    void testSetPassword() {
        Receptionist receptionist = new Receptionist();
        receptionist.setPassword("newpass");
        assertEquals("newpass", receptionist.getPassword());
    }

    @Test
    void testSetName() {
        Receptionist receptionist = new Receptionist();
        receptionist.setName("New Name");
        assertEquals("New Name", receptionist.getName());
    }

    @Test
    void testSetContactNumber() {
        Receptionist receptionist = new Receptionist();
        receptionist.setContactNumber("555-9999");
        assertEquals("555-9999", receptionist.getContactNumber());
    }

    @Test
    void testSetEmail() {
        Receptionist receptionist = new Receptionist();
        receptionist.setEmail("newemail@test.com");
        assertEquals("newemail@test.com", receptionist.getEmail());
    }
}
