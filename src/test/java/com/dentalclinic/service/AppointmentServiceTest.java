package com.dentalclinic.service;

import com.dentalclinic.dao.AppointmentDAO;
import com.dentalclinic.model.Appointment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentDAO appointmentDAO;

    @InjectMocks
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        appointmentService = new AppointmentService(appointmentDAO);
    }

    @Test
    void testAddAppointment_Success() {
        Appointment appointment = new Appointment(1, 10, 20, 30, "2024-01-15", "10:00");
        when(appointmentDAO.addAppointment(any(Appointment.class))).thenReturn(1);

        int result = appointmentService.addAppointment(appointment);

        assertEquals(1, result);
        verify(appointmentDAO).addAppointment(appointment);
    }

    @Test
    void testUpdateAppointment_Success() {
        Appointment appointment = new Appointment(1, 10, 20, 30, "2024-01-15", "10:00");
        when(appointmentDAO.updateAppointment(any(Appointment.class))).thenReturn(true);

        boolean result = appointmentService.updateAppointment(appointment);

        assertTrue(result);
        verify(appointmentDAO).updateAppointment(appointment);
    }

    @Test
    void testUpdateAppointment_Failure() {
        Appointment appointment = new Appointment(1, 10, 20, 30, "2024-01-15", "10:00");
        when(appointmentDAO.updateAppointment(any(Appointment.class))).thenReturn(false);

        boolean result = appointmentService.updateAppointment(appointment);

        assertFalse(result);
        verify(appointmentDAO).updateAppointment(appointment);
    }

    @Test
    void testCancelAppointment_Success() {
        when(appointmentDAO.cancelAppointment(anyInt())).thenReturn(true);

        boolean result = appointmentService.cancelAppointment(1);

        assertTrue(result);
        verify(appointmentDAO).cancelAppointment(1);
    }

    @Test
    void testCancelAppointment_Failure() {
        when(appointmentDAO.cancelAppointment(anyInt())).thenReturn(false);

        boolean result = appointmentService.cancelAppointment(1);

        assertFalse(result);
        verify(appointmentDAO).cancelAppointment(1);
    }

    @Test
    void testDeleteAppointment_Success() {
        when(appointmentDAO.deleteAppointment(anyInt())).thenReturn(true);

        boolean result = appointmentService.deleteAppointment(1);

        assertTrue(result);
        verify(appointmentDAO).deleteAppointment(1);
    }

    @Test
    void testDeleteAppointment_Failure() {
        when(appointmentDAO.deleteAppointment(anyInt())).thenReturn(false);

        boolean result = appointmentService.deleteAppointment(1);

        assertFalse(result);
        verify(appointmentDAO).deleteAppointment(1);
    }

    @Test
    void testGetAppointmentById_Success() {
        Appointment appointment = new Appointment(1, 10, 20, 30, "2024-01-15", "10:00");
        when(appointmentDAO.getAppointmentById(1)).thenReturn(appointment);

        Appointment result = appointmentService.getAppointmentById(1);

        assertNotNull(result);
        assertEquals(1, result.getAppointmentId());
        verify(appointmentDAO).getAppointmentById(1);
    }

    @Test
    void testGetAppointmentById_NotFound() {
        when(appointmentDAO.getAppointmentById(999)).thenReturn(null);

        Appointment result = appointmentService.getAppointmentById(999);

        assertNull(result);
        verify(appointmentDAO).getAppointmentById(999);
    }

    @Test
    void testSearchAppointments() {
        Appointment appointment1 = new Appointment(1, 10, 20, 30, "2024-01-15", "10:00");
        Appointment appointment2 = new Appointment(2, 11, 21, 31, "2024-01-16", "11:00");
        List<Appointment> expectedAppointments = Arrays.asList(appointment1, appointment2);

        when(appointmentDAO.searchAppointments(anyString())).thenReturn(expectedAppointments);

        List<Appointment> result = appointmentService.searchAppointments("John");

        assertEquals(2, result.size());
        verify(appointmentDAO).searchAppointments("John");
    }

    @Test
    void testGetAllAppointments() {
        Appointment appointment1 = new Appointment(1, 10, 20, 30, "2024-01-15", "10:00");
        Appointment appointment2 = new Appointment(2, 11, 21, 31, "2024-01-16", "11:00");
        List<Appointment> expectedAppointments = Arrays.asList(appointment1, appointment2);

        when(appointmentDAO.getAllAppointments()).thenReturn(expectedAppointments);

        List<Appointment> result = appointmentService.getAllAppointments();

        assertEquals(2, result.size());
        verify(appointmentDAO).getAllAppointments();
    }

    @Test
    void testGetAppointmentsByDate() {
        Appointment appointment1 = new Appointment(1, 10, 20, 30, "2024-01-15", "10:00");
        Appointment appointment2 = new Appointment(2, 11, 21, 31, "2024-01-15", "11:00");
        List<Appointment> expectedAppointments = Arrays.asList(appointment1, appointment2);

        when(appointmentDAO.getAppointmentsByDate(anyString())).thenReturn(expectedAppointments);

        List<Appointment> result = appointmentService.getAppointmentsByDate("2024-01-15");

        assertEquals(2, result.size());
        verify(appointmentDAO).getAppointmentsByDate("2024-01-15");
    }
}
