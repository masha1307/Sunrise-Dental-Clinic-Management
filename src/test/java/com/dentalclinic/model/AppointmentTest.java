package com.dentalclinic.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {

    @Test
    void testDefaultConstructor() {
        Appointment appointment = new Appointment();
        assertEquals(0, appointment.getAppointmentId());
        assertEquals(0, appointment.getPatientId());
        assertEquals(0, appointment.getDentistId());
        assertEquals(0, appointment.getTreatmentId());
        assertNull(appointment.getAppointmentDate());
        assertNull(appointment.getAppointmentTime());
        assertNull(appointment.getStatus());
        assertNull(appointment.getPatientName());
        assertNull(appointment.getDentistName());
        assertNull(appointment.getTreatmentName());
    }

    @Test
    void testParameterizedConstructor() {
        Appointment appointment = new Appointment(1, 10, 20, 30, "2024-01-15", "10:00");
        assertEquals(1, appointment.getAppointmentId());
        assertEquals(10, appointment.getPatientId());
        assertEquals(20, appointment.getDentistId());
        assertEquals(30, appointment.getTreatmentId());
        assertEquals("2024-01-15", appointment.getAppointmentDate());
        assertEquals("10:00", appointment.getAppointmentTime());
    }

    @Test
    void testSettersAndGetters() {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(2);
        appointment.setPatientId(11);
        appointment.setDentistId(21);
        appointment.setTreatmentId(31);
        appointment.setAppointmentDate("2024-02-20");
        appointment.setAppointmentTime("14:30");
        appointment.setStatus("Scheduled");
        appointment.setPatientName("John Doe");
        appointment.setDentistName("Dr. Smith");
        appointment.setTreatmentName("Root Canal");

        assertEquals(2, appointment.getAppointmentId());
        assertEquals(11, appointment.getPatientId());
        assertEquals(21, appointment.getDentistId());
        assertEquals(31, appointment.getTreatmentId());
        assertEquals("2024-02-20", appointment.getAppointmentDate());
        assertEquals("14:30", appointment.getAppointmentTime());
        assertEquals("Scheduled", appointment.getStatus());
        assertEquals("John Doe", appointment.getPatientName());
        assertEquals("Dr. Smith", appointment.getDentistName());
        assertEquals("Root Canal", appointment.getTreatmentName());
    }

    @Test
    void testSetAppointmentId() {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(100);
        assertEquals(100, appointment.getAppointmentId());
    }

    @Test
    void testSetPatientId() {
        Appointment appointment = new Appointment();
        appointment.setPatientId(200);
        assertEquals(200, appointment.getPatientId());
    }

    @Test
    void testSetDentistId() {
        Appointment appointment = new Appointment();
        appointment.setDentistId(300);
        assertEquals(300, appointment.getDentistId());
    }

    @Test
    void testSetTreatmentId() {
        Appointment appointment = new Appointment();
        appointment.setTreatmentId(400);
        assertEquals(400, appointment.getTreatmentId());
    }

    @Test
    void testSetAppointmentDate() {
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate("2024-12-31");
        assertEquals("2024-12-31", appointment.getAppointmentDate());
    }

    @Test
    void testSetAppointmentTime() {
        Appointment appointment = new Appointment();
        appointment.setAppointmentTime("09:00");
        assertEquals("09:00", appointment.getAppointmentTime());
    }

    @Test
    void testSetStatus() {
        Appointment appointment = new Appointment();
        appointment.setStatus("Completed");
        assertEquals("Completed", appointment.getStatus());
    }

    @Test
    void testSetPatientName() {
        Appointment appointment = new Appointment();
        appointment.setPatientName("Jane Doe");
        assertEquals("Jane Doe", appointment.getPatientName());
    }

    @Test
    void testSetDentistName() {
        Appointment appointment = new Appointment();
        appointment.setDentistName("Dr. Johnson");
        assertEquals("Dr. Johnson", appointment.getDentistName());
    }

    @Test
    void testSetTreatmentName() {
        Appointment appointment = new Appointment();
        appointment.setTreatmentName("Cleaning");
        assertEquals("Cleaning", appointment.getTreatmentName());
    }
}
