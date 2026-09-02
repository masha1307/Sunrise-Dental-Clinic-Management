package com.dentalclinic.service;

import com.dentalclinic.dao.AdminDAO;
import com.dentalclinic.dao.ReceptionistDAO;
import com.dentalclinic.model.Admin;
import com.dentalclinic.model.Receptionist;
import com.dentalclinic.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private AdminDAO adminDAO;

    @Mock
    private ReceptionistDAO receptionistDAO;

    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        loginService = new LoginService(adminDAO, receptionistDAO);
    }

    @Test
    void testLogin_SuccessfulAdmin() {
        when(adminDAO.login("admin", "admin123")).thenReturn(true);

        boolean result = loginService.login("admin", "admin123");

        assertTrue(result);
        verify(adminDAO).login("admin", "admin123");
        verify(receptionistDAO, never()).login(anyString(), anyString());
    }

    @Test
    void testLogin_SuccessfulReceptionist() {
        when(adminDAO.login("receptionist", "recep123")).thenReturn(false);
        when(receptionistDAO.login("receptionist", "recep123")).thenReturn(true);

        boolean result = loginService.login("receptionist", "recep123");

        assertTrue(result);
        verify(adminDAO).login("receptionist", "recep123");
        verify(receptionistDAO).login("receptionist", "recep123");
    }

    @Test
    void testLogin_FailedInvalidCredentials() {
        when(adminDAO.login("invalid", "wrong")).thenReturn(false);
        when(receptionistDAO.login("invalid", "wrong")).thenReturn(false);

        boolean result = loginService.login("invalid", "wrong");

        assertFalse(result);
        verify(adminDAO).login("invalid", "wrong");
        verify(receptionistDAO).login("invalid", "wrong");
    }

    @Test
    void testLogin_NullUsername() {
        boolean result = loginService.login(null, "password");

        assertFalse(result);
        verify(adminDAO, never()).login(anyString(), anyString());
        verify(receptionistDAO, never()).login(anyString(), anyString());
    }

    @Test
    void testLogin_EmptyUsername() {
        boolean result = loginService.login("", "password");

        assertFalse(result);
        verify(adminDAO, never()).login(anyString(), anyString());
        verify(receptionistDAO, never()).login(anyString(), anyString());
    }

    @Test
    void testLogin_WhitespaceUsername() {
        boolean result = loginService.login("   ", "password");

        assertFalse(result);
        verify(adminDAO, never()).login(anyString(), anyString());
        verify(receptionistDAO, never()).login(anyString(), anyString());
    }

    @Test
    void testLogin_NullPassword() {
        boolean result = loginService.login("admin", null);

        assertFalse(result);
        verify(adminDAO, never()).login(anyString(), anyString());
        verify(receptionistDAO, never()).login(anyString(), anyString());
    }

    @Test
    void testLogin_EmptyPassword() {
        boolean result = loginService.login("admin", "");

        assertFalse(result);
        verify(adminDAO, never()).login(anyString(), anyString());
        verify(receptionistDAO, never()).login(anyString(), anyString());
    }

    @Test
    void testLogin_WhitespacePassword() {
        boolean result = loginService.login("admin", "   ");

        assertFalse(result);
        verify(adminDAO, never()).login(anyString(), anyString());
        verify(receptionistDAO, never()).login(anyString(), anyString());
    }

    @Test
    void testLogin_UsernameAndPasswordTrimmed() {
        when(adminDAO.login("admin", "admin123")).thenReturn(true);

        boolean result = loginService.login("  admin  ", "  admin123  ");

        assertTrue(result);
        verify(adminDAO).login("admin", "admin123");
    }

    @Test
    void testGetUserByUsername_SuccessfulAdmin() {
        Admin admin = new Admin();
        admin.setAdminId(1);
        admin.setUsername("admin");
        admin.setPassword("admin123");
        admin.setName("System Administrator");
        admin.setEmail("admin@dentalclinic.com");

        when(adminDAO.getAdminByUsername("admin")).thenReturn(admin);

        User result = loginService.getUserByUsername("admin");

        assertNotNull(result);
        assertEquals(1, result.getUserId());
        assertEquals("admin", result.getUsername());
        assertEquals("admin123", result.getPassword());
        assertEquals("admin", result.getRole());
        verify(adminDAO).getAdminByUsername("admin");
        verify(receptionistDAO, never()).getReceptionistByUsername(anyString());
    }

    @Test
    void testGetUserByUsername_SuccessfulReceptionist() {
        when(adminDAO.getAdminByUsername("receptionist")).thenReturn(null);

        Receptionist receptionist = new Receptionist();
        receptionist.setReceptionistId(2);
        receptionist.setUsername("receptionist");
        receptionist.setPassword("recep123");
        receptionist.setName("Emily Davis");
        receptionist.setContactNumber("555-0201");
        receptionist.setEmail("emily@dentalclinic.com");

        when(receptionistDAO.getReceptionistByUsername("receptionist")).thenReturn(receptionist);

        User result = loginService.getUserByUsername("receptionist");

        assertNotNull(result);
        assertEquals(2, result.getUserId());
        assertEquals("receptionist", result.getUsername());
        assertEquals("recep123", result.getPassword());
        assertEquals("receptionist", result.getRole());
        verify(adminDAO).getAdminByUsername("receptionist");
        verify(receptionistDAO).getReceptionistByUsername("receptionist");
    }

    @Test
    void testGetUserByUsername_UserNotFound() {
        when(adminDAO.getAdminByUsername("nonexistent")).thenReturn(null);
        when(receptionistDAO.getReceptionistByUsername("nonexistent")).thenReturn(null);

        User result = loginService.getUserByUsername("nonexistent");

        assertNull(result);
        verify(adminDAO).getAdminByUsername("nonexistent");
        verify(receptionistDAO).getReceptionistByUsername("nonexistent");
    }

    @Test
    void testGetUserByUsername_NullUsername() {
        User result = loginService.getUserByUsername(null);

        assertNull(result);
        verify(adminDAO, never()).getAdminByUsername(anyString());
        verify(receptionistDAO, never()).getReceptionistByUsername(anyString());
    }

    @Test
    void testGetUserByUsername_EmptyUsername() {
        User result = loginService.getUserByUsername("");

        assertNull(result);
        verify(adminDAO, never()).getAdminByUsername(anyString());
        verify(receptionistDAO, never()).getReceptionistByUsername(anyString());
    }

    @Test
    void testGetUserByUsername_WhitespaceUsername() {
        User result = loginService.getUserByUsername("   ");

        assertNull(result);
        verify(adminDAO, never()).getAdminByUsername(anyString());
        verify(receptionistDAO, never()).getReceptionistByUsername(anyString());
    }

    @Test
    void testGetUserByUsername_UsernameTrimmed() {
        Admin admin = new Admin();
        admin.setAdminId(1);
        admin.setUsername("admin");
        admin.setPassword("admin123");
        admin.setName("System Administrator");
        admin.setEmail("admin@dentalclinic.com");

        when(adminDAO.getAdminByUsername("admin")).thenReturn(admin);

        User result = loginService.getUserByUsername("  admin  ");

        assertNotNull(result);
        verify(adminDAO).getAdminByUsername("admin");
    }
}
