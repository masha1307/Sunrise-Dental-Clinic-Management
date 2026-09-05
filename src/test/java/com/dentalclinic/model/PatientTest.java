package com.dentalclinic.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PatientTest {

    @Test
    void testDefaultConstructor() {
        Patient patient = new Patient();
        assertEquals(0, patient.getPatientId());
        assertNull(patient.getPatientName());
        assertEquals(0, patient.getAge());
        assertNull(patient.getGender());
        assertNull(patient.getContactNumber());
        assertNull(patient.getEmail());
        assertNull(patient.getAddress());
    }

    @Test
    void testParameterizedConstructor() {
        Patient patient = new Patient(1, "John Doe", 35, "Male", "555-0101", "john@email.com", "123 Main St");
        assertEquals(1, patient.getPatientId());
        assertEquals("John Doe", patient.getPatientName());
        assertEquals(35, patient.getAge());
        assertEquals("Male", patient.getGender());
        assertEquals("555-0101", patient.getContactNumber());
        assertEquals("john@email.com", patient.getEmail());
        assertEquals("123 Main St", patient.getAddress());
    }

    @Test
    void testSettersAndGetters() {
        Patient patient = new Patient();
        patient.setPatientId(2);
        patient.setPatientName("Jane Smith");
        patient.setAge(28);
        patient.setGender("Female");
        patient.setContactNumber("555-0102");
        patient.setEmail("jane@email.com");
        patient.setAddress("456 Oak Ave");

        assertEquals(2, patient.getPatientId());
        assertEquals("Jane Smith", patient.getPatientName());
        assertEquals(28, patient.getAge());
        assertEquals("Female", patient.getGender());
        assertEquals("555-0102", patient.getContactNumber());
        assertEquals("jane@email.com", patient.getEmail());
        assertEquals("456 Oak Ave", patient.getAddress());
    }

    @Test
    void testSetPatientId() {
        Patient patient = new Patient();
        patient.setPatientId(100);
        assertEquals(100, patient.getPatientId());
    }

    @Test
    void testSetPatientName() {
        Patient patient = new Patient();
        patient.setPatientName("Test Patient");
        assertEquals("Test Patient", patient.getPatientName());
    }

    @Test
    void testSetAge() {
        Patient patient = new Patient();
        patient.setAge(50);
        assertEquals(50, patient.getAge());
    }

    @Test
    void testSetGender() {
        Patient patient = new Patient();
        patient.setGender("Other");
        assertEquals("Other", patient.getGender());
    }

    @Test
    void testSetContactNumber() {
        Patient patient = new Patient();
        patient.setContactNumber("555-9999");
        assertEquals("555-9999", patient.getContactNumber());
    }

    @Test
    void testSetEmail() {
        Patient patient = new Patient();
        patient.setEmail("test@test.com");
        assertEquals("test@test.com", patient.getEmail());
    }

    @Test
    void testSetAddress() {
        Patient patient = new Patient();
        patient.setAddress("789 Pine Rd");
        assertEquals("789 Pine Rd", patient.getAddress());
    }
}
