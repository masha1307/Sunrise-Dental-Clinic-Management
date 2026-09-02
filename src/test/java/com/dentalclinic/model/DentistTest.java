package com.dentalclinic.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DentistTest {

    @Test
    void testDefaultConstructor() {
        Dentist dentist = new Dentist();
        assertEquals(0, dentist.getDentistId());
        assertNull(dentist.getDentistName());
        assertNull(dentist.getSpecialization());
        assertNull(dentist.getContactNumber());
        assertNull(dentist.getEmail());
    }

    @Test
    void testParameterizedConstructor() {
        Dentist dentist = new Dentist(1, "Dr. John Smith", "Orthodontist", "555-0101", "john.smith@clinic.com");
        assertEquals(1, dentist.getDentistId());
        assertEquals("Dr. John Smith", dentist.getDentistName());
        assertEquals("Orthodontist", dentist.getSpecialization());
        assertEquals("555-0101", dentist.getContactNumber());
        assertEquals("john.smith@clinic.com", dentist.getEmail());
    }

    @Test
    void testSettersAndGetters() {
        Dentist dentist = new Dentist();
        dentist.setDentistId(2);
        dentist.setDentistName("Dr. Sarah Johnson");
        dentist.setSpecialization("General Dentist");
        dentist.setContactNumber("555-0102");
        dentist.setEmail("sarah.johnson@clinic.com");

        assertEquals(2, dentist.getDentistId());
        assertEquals("Dr. Sarah Johnson", dentist.getDentistName());
        assertEquals("General Dentist", dentist.getSpecialization());
        assertEquals("555-0102", dentist.getContactNumber());
        assertEquals("sarah.johnson@clinic.com", dentist.getEmail());
    }

    @Test
    void testSetDentistId() {
        Dentist dentist = new Dentist();
        dentist.setDentistId(100);
        assertEquals(100, dentist.getDentistId());
    }

    @Test
    void testSetDentistName() {
        Dentist dentist = new Dentist();
        dentist.setDentistName("Dr. Test");
        assertEquals("Dr. Test", dentist.getDentistName());
    }

    @Test
    void testSetSpecialization() {
        Dentist dentist = new Dentist();
        dentist.setSpecialization("Oral Surgeon");
        assertEquals("Oral Surgeon", dentist.getSpecialization());
    }

    @Test
    void testSetContactNumber() {
        Dentist dentist = new Dentist();
        dentist.setContactNumber("555-9999");
        assertEquals("555-9999", dentist.getContactNumber());
    }

    @Test
    void testSetEmail() {
        Dentist dentist = new Dentist();
        dentist.setEmail("test@test.com");
        assertEquals("test@test.com", dentist.getEmail());
    }
}
