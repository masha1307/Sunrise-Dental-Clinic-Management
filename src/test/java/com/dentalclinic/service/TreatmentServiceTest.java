package com.dentalclinic.service;

import com.dentalclinic.dao.TreatmentDAO;
import com.dentalclinic.model.Treatment;
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
class TreatmentServiceTest {

    @Mock
    private TreatmentDAO treatmentDAO;

    @InjectMocks
    private TreatmentService treatmentService;

    @BeforeEach
    void setUp() {
        treatmentService = new TreatmentService(treatmentDAO);
    }

    @Test
    void testGetAllTreatments() {
        Treatment treatment1 = new Treatment();
        treatment1.setTreatmentId(1);
        treatment1.setTreatmentName("Teeth Cleaning");
        treatment1.setPrice(50.0);
        treatment1.setDurationMinutes("30");

        Treatment treatment2 = new Treatment();
        treatment2.setTreatmentId(2);
        treatment2.setTreatmentName("Root Canal");
        treatment2.setPrice(200.0);
        treatment2.setDurationMinutes("60");

        List<Treatment> expectedTreatments = Arrays.asList(treatment1, treatment2);

        when(treatmentDAO.getAllTreatments()).thenReturn(expectedTreatments);

        List<Treatment> result = treatmentService.getAllTreatments();

        assertEquals(2, result.size());
        assertEquals("Teeth Cleaning", result.get(0).getTreatmentName());
        assertEquals("Root Canal", result.get(1).getTreatmentName());
        verify(treatmentDAO).getAllTreatments();
    }

    @Test
    void testGetTreatmentById_Success() {
        Treatment treatment = new Treatment();
        treatment.setTreatmentId(1);
        treatment.setTreatmentName("Teeth Cleaning");
        treatment.setPrice(50.0);
        treatment.setDurationMinutes("30");

        when(treatmentDAO.getTreatmentById(1)).thenReturn(treatment);

        Treatment result = treatmentService.getTreatmentById(1);

        assertNotNull(result);
        assertEquals("Teeth Cleaning", result.getTreatmentName());
        verify(treatmentDAO).getTreatmentById(1);
    }

    @Test
    void testGetTreatmentById_NotFound() {
        when(treatmentDAO.getTreatmentById(999)).thenReturn(null);

        Treatment result = treatmentService.getTreatmentById(999);

        assertNull(result);
        verify(treatmentDAO).getTreatmentById(999);
    }

    @Test
    void testAddTreatment_Success() {
        Treatment treatment = new Treatment();
        treatment.setTreatmentId(1);
        treatment.setTreatmentName("Teeth Cleaning");
        treatment.setPrice(50.0);
        treatment.setDurationMinutes("30");

        when(treatmentDAO.addTreatment(any(Treatment.class))).thenReturn(true);

        boolean result = treatmentService.addTreatment(treatment);

        assertTrue(result);
        verify(treatmentDAO).addTreatment(treatment);
    }

    @Test
    void testAddTreatment_Failure() {
        Treatment treatment = new Treatment();
        treatment.setTreatmentId(1);
        treatment.setTreatmentName("Teeth Cleaning");
        treatment.setPrice(50.0);
        treatment.setDurationMinutes("30");

        when(treatmentDAO.addTreatment(any(Treatment.class))).thenReturn(false);

        boolean result = treatmentService.addTreatment(treatment);

        assertFalse(result);
        verify(treatmentDAO).addTreatment(treatment);
    }

    @Test
    void testUpdateTreatment_Success() {
        Treatment treatment = new Treatment();
        treatment.setTreatmentId(1);
        treatment.setTreatmentName("Teeth Cleaning");
        treatment.setPrice(50.0);
        treatment.setDurationMinutes("30");

        when(treatmentDAO.updateTreatment(any(Treatment.class))).thenReturn(true);

        boolean result = treatmentService.updateTreatment(treatment);

        assertTrue(result);
        verify(treatmentDAO).updateTreatment(treatment);
    }

    @Test
    void testUpdateTreatment_Failure() {
        Treatment treatment = new Treatment();
        treatment.setTreatmentId(1);
        treatment.setTreatmentName("Teeth Cleaning");
        treatment.setPrice(50.0);
        treatment.setDurationMinutes("30");

        when(treatmentDAO.updateTreatment(any(Treatment.class))).thenReturn(false);

        boolean result = treatmentService.updateTreatment(treatment);

        assertFalse(result);
        verify(treatmentDAO).updateTreatment(treatment);
    }

    @Test
    void testDeleteTreatment_Success() {
        when(treatmentDAO.deleteTreatment(anyInt())).thenReturn(true);

        boolean result = treatmentService.deleteTreatment(1);

        assertTrue(result);
        verify(treatmentDAO).deleteTreatment(1);
    }

    @Test
    void testDeleteTreatment_Failure() {
        when(treatmentDAO.deleteTreatment(anyInt())).thenReturn(false);

        boolean result = treatmentService.deleteTreatment(1);

        assertFalse(result);
        verify(treatmentDAO).deleteTreatment(1);
    }
}
