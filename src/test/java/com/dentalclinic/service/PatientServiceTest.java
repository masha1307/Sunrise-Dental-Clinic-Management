package com.dentalclinic.service;

import com.dentalclinic.dao.PatientDAO;
import com.dentalclinic.model.Patient;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientDAO patientDAO;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        patientService = new PatientService(patientDAO);
    }

    @Test
    void testGetAllPatients() {
        Patient patient1 = new Patient(1, "John Doe", 35, "Male", "555-0101", "john@email.com", "123 Main St");
        Patient patient2 = new Patient(2, "Jane Smith", 28, "Female", "555-0102", "jane@email.com", "456 Oak Ave");
        List<Patient> expectedPatients = Arrays.asList(patient1, patient2);

        when(patientDAO.getAllPatients()).thenReturn(expectedPatients);

        List<Patient> result = patientService.getAllPatients();

        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getPatientName());
        assertEquals("Jane Smith", result.get(1).getPatientName());
        verify(patientDAO).getAllPatients();
    }

    @Test
    void testAddPatient_Success() {
        Patient patient = new Patient(1, "John Doe", 35, "Male", "555-0101", "john@email.com", "123 Main St");
        when(patientDAO.addPatient(any(Patient.class))).thenReturn(true);

        boolean result = patientService.addPatient(patient);

        assertTrue(result);
        verify(patientDAO).addPatient(patient);
    }

    @Test
    void testAddPatient_Failure() {
        Patient patient = new Patient(1, "John Doe", 35, "Male", "555-0101", "john@email.com", "123 Main St");
        when(patientDAO.addPatient(any(Patient.class))).thenReturn(false);

        boolean result = patientService.addPatient(patient);

        assertFalse(result);
        verify(patientDAO).addPatient(patient);
    }

    @Test
    void testGetPatientById_Success() {
        Patient patient = new Patient(1, "John Doe", 35, "Male", "555-0101", "john@email.com", "123 Main St");
        when(patientDAO.getPatientById(1)).thenReturn(patient);

        Patient result = patientService.getPatientById(1);

        assertNotNull(result);
        assertEquals("John Doe", result.getPatientName());
        verify(patientDAO).getPatientById(1);
    }

    @Test
    void testGetPatientById_NotFound() {
        when(patientDAO.getPatientById(999)).thenReturn(null);

        Patient result = patientService.getPatientById(999);

        assertNull(result);
        verify(patientDAO).getPatientById(999);
    }

    @Test
    void testUpdatePatient_Success() {
        Patient patient = new Patient(1, "John Doe", 35, "Male", "555-0101", "john@email.com", "123 Main St");
        when(patientDAO.updatePatient(any(Patient.class))).thenReturn(true);

        boolean result = patientService.updatePatient(patient);

        assertTrue(result);
        verify(patientDAO).updatePatient(patient);
    }

    @Test
    void testUpdatePatient_Failure() {
        Patient patient = new Patient(1, "John Doe", 35, "Male", "555-0101", "john@email.com", "123 Main St");
        when(patientDAO.updatePatient(any(Patient.class))).thenReturn(false);

        boolean result = patientService.updatePatient(patient);

        assertFalse(result);
        verify(patientDAO).updatePatient(patient);
    }

    @Test
    void testDeletePatient_Success() {
        when(patientDAO.deletePatient(anyInt())).thenReturn(true);

        boolean result = patientService.deletePatient(1);

        assertTrue(result);
        verify(patientDAO).deletePatient(1);
    }

    @Test
    void testDeletePatient_Failure() {
        when(patientDAO.deletePatient(anyInt())).thenReturn(false);

        boolean result = patientService.deletePatient(1);

        assertFalse(result);
        verify(patientDAO).deletePatient(1);
    }
}
