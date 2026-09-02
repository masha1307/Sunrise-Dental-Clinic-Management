package com.dentalclinic.service;

import com.dentalclinic.dao.DentistDAO;
import com.dentalclinic.model.Dentist;
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
class DentistServiceTest {

    @Mock
    private DentistDAO dentistDAO;

    @InjectMocks
    private DentistService dentistService;

    @BeforeEach
    void setUp() {
        dentistService = new DentistService(dentistDAO);
    }

    @Test
    void testGetAllDentists() {
        Dentist dentist1 = new Dentist(1, "Dr. John Smith", "Orthodontist", "555-0101", "john.smith@clinic.com");
        Dentist dentist2 = new Dentist(2, "Dr. Sarah Johnson", "General Dentist", "555-0102", "sarah.johnson@clinic.com");
        List<Dentist> expectedDentists = Arrays.asList(dentist1, dentist2);

        when(dentistDAO.getAllDentists()).thenReturn(expectedDentists);

        List<Dentist> result = dentistService.getAllDentists();

        assertEquals(2, result.size());
        assertEquals("Dr. John Smith", result.get(0).getDentistName());
        assertEquals("Dr. Sarah Johnson", result.get(1).getDentistName());
        verify(dentistDAO).getAllDentists();
    }

    @Test
    void testGetDentistById_Success() {
        Dentist dentist = new Dentist(1, "Dr. John Smith", "Orthodontist", "555-0101", "john.smith@clinic.com");
        when(dentistDAO.getDentistById(1)).thenReturn(dentist);

        Dentist result = dentistService.getDentistById(1);

        assertNotNull(result);
        assertEquals("Dr. John Smith", result.getDentistName());
        verify(dentistDAO).getDentistById(1);
    }

    @Test
    void testGetDentistById_NotFound() {
        when(dentistDAO.getDentistById(999)).thenReturn(null);

        Dentist result = dentistService.getDentistById(999);

        assertNull(result);
        verify(dentistDAO).getDentistById(999);
    }

    @Test
    void testAddDentist_Success() {
        Dentist dentist = new Dentist(1, "Dr. John Smith", "Orthodontist", "555-0101", "john.smith@clinic.com");
        when(dentistDAO.addDentist(any(Dentist.class))).thenReturn(true);

        boolean result = dentistService.addDentist(dentist);

        assertTrue(result);
        verify(dentistDAO).addDentist(dentist);
    }

    @Test
    void testAddDentist_Failure() {
        Dentist dentist = new Dentist(1, "Dr. John Smith", "Orthodontist", "555-0101", "john.smith@clinic.com");
        when(dentistDAO.addDentist(any(Dentist.class))).thenReturn(false);

        boolean result = dentistService.addDentist(dentist);

        assertFalse(result);
        verify(dentistDAO).addDentist(dentist);
    }

    @Test
    void testUpdateDentist_Success() {
        Dentist dentist = new Dentist(1, "Dr. John Smith", "Orthodontist", "555-0101", "john.smith@clinic.com");
        when(dentistDAO.updateDentist(any(Dentist.class))).thenReturn(true);

        boolean result = dentistService.updateDentist(dentist);

        assertTrue(result);
        verify(dentistDAO).updateDentist(dentist);
    }

    @Test
    void testUpdateDentist_Failure() {
        Dentist dentist = new Dentist(1, "Dr. John Smith", "Orthodontist", "555-0101", "john.smith@clinic.com");
        when(dentistDAO.updateDentist(any(Dentist.class))).thenReturn(false);

        boolean result = dentistService.updateDentist(dentist);

        assertFalse(result);
        verify(dentistDAO).updateDentist(dentist);
    }

    @Test
    void testDeleteDentist_Success() {
        when(dentistDAO.deleteDentist(anyInt())).thenReturn(true);

        boolean result = dentistService.deleteDentist(1);

        assertTrue(result);
        verify(dentistDAO).deleteDentist(1);
    }

    @Test
    void testDeleteDentist_Failure() {
        when(dentistDAO.deleteDentist(anyInt())).thenReturn(false);

        boolean result = dentistService.deleteDentist(1);

        assertFalse(result);
        verify(dentistDAO).deleteDentist(1);
    }
}
