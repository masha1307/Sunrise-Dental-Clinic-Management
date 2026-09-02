package com.dentalclinic.service;

import com.dentalclinic.dao.AdminDAO;
import com.dentalclinic.dao.ReceptionistDAO;
import com.dentalclinic.model.Admin;
import com.dentalclinic.model.Receptionist;
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
class ReceptionistServiceTest {

    @Mock
    private ReceptionistDAO receptionistDAO;

    @Mock
    private AdminDAO adminDAO;

    @InjectMocks
    private ReceptionistService receptionistService;

    @BeforeEach
    void setUp() {
        receptionistService = new ReceptionistService(receptionistDAO, adminDAO);
    }

    // ================= Receptionist Operations =================

    @Test
    void testGetAllReceptionists() {
        Receptionist receptionist1 = new Receptionist(1, "receptionist", "recep123", "Emily Davis", "555-0201", "emily@dentalclinic.com");
        Receptionist receptionist2 = new Receptionist(2, "receptionist2", "recep456", "John Smith", "555-0202", "john@dentalclinic.com");
        List<Receptionist> expectedReceptionists = Arrays.asList(receptionist1, receptionist2);

        when(receptionistDAO.getAllReceptionists()).thenReturn(expectedReceptionists);

        List<Receptionist> result = receptionistService.getAllReceptionists();

        assertEquals(2, result.size());
        assertEquals("Emily Davis", result.get(0).getName());
        assertEquals("John Smith", result.get(1).getName());
        verify(receptionistDAO).getAllReceptionists();
    }

    @Test
    void testGetReceptionistById_Success() {
        Receptionist receptionist = new Receptionist(1, "receptionist", "recep123", "Emily Davis", "555-0201", "emily@dentalclinic.com");
        when(receptionistDAO.getReceptionistById(1)).thenReturn(receptionist);

        Receptionist result = receptionistService.getReceptionistById(1);

        assertNotNull(result);
        assertEquals("Emily Davis", result.getName());
        verify(receptionistDAO).getReceptionistById(1);
    }

    @Test
    void testGetReceptionistById_NotFound() {
        when(receptionistDAO.getReceptionistById(999)).thenReturn(null);

        Receptionist result = receptionistService.getReceptionistById(999);

        assertNull(result);
        verify(receptionistDAO).getReceptionistById(999);
    }

    @Test
    void testIsReceptionistUsernameTaken_True() {
        when(receptionistDAO.isUsernameTaken(anyString(), anyInt())).thenReturn(true);

        boolean result = receptionistService.isReceptionistUsernameTaken("testuser", 0);

        assertTrue(result);
        verify(receptionistDAO).isUsernameTaken("testuser", 0);
    }

    @Test
    void testIsReceptionistUsernameTaken_False() {
        when(receptionistDAO.isUsernameTaken(anyString(), anyInt())).thenReturn(false);
        when(adminDAO.isUsernameTaken(anyString(), anyInt())).thenReturn(false);

        boolean result = receptionistService.isReceptionistUsernameTaken("testuser", 0);

        assertFalse(result);
        verify(receptionistDAO).isUsernameTaken("testuser", 0);
        verify(adminDAO).isUsernameTaken("testuser", 0);
    }

    @Test
    void testIsReceptionistUsernameTaken_NullUsername() {
        boolean result = receptionistService.isReceptionistUsernameTaken(null, 0);

        assertFalse(result);
        verify(receptionistDAO, never()).isUsernameTaken(anyString(), anyInt());
        verify(adminDAO, never()).isUsernameTaken(anyString(), anyInt());
    }

    @Test
    void testIsReceptionistUsernameTaken_EmptyUsername() {
        boolean result = receptionistService.isReceptionistUsernameTaken("   ", 0);

        assertFalse(result);
        verify(receptionistDAO, never()).isUsernameTaken(anyString(), anyInt());
        verify(adminDAO, never()).isUsernameTaken(anyString(), anyInt());
    }

    @Test
    void testAddReceptionist_Success() {
        Receptionist receptionist = new Receptionist(0, "newuser", "newpass", "New User", "555-0203", "new@dentalclinic.com");
        when(receptionistDAO.isUsernameTaken(anyString(), anyInt())).thenReturn(false);
        when(adminDAO.isUsernameTaken(anyString(), anyInt())).thenReturn(false);
        when(receptionistDAO.addReceptionist(any(Receptionist.class))).thenReturn(true);

        boolean result = receptionistService.addReceptionist(receptionist);

        assertTrue(result);
        verify(receptionistDAO).addReceptionist(any(Receptionist.class));
    }

    @Test
    void testAddReceptionist_NullReceptionist() {
        boolean result = receptionistService.addReceptionist(null);

        assertFalse(result);
        verify(receptionistDAO, never()).addReceptionist(any(Receptionist.class));
    }

    @Test
    void testAddReceptionist_NullUsername() {
        Receptionist receptionist = new Receptionist(0, null, "pass", "Name", "555-0203", "email@test.com");

        boolean result = receptionistService.addReceptionist(receptionist);

        assertFalse(result);
        verify(receptionistDAO, never()).addReceptionist(any(Receptionist.class));
    }

    @Test
    void testAddReceptionist_NullPassword() {
        Receptionist receptionist = new Receptionist(0, "user", null, "Name", "555-0203", "email@test.com");

        boolean result = receptionistService.addReceptionist(receptionist);

        assertFalse(result);
        verify(receptionistDAO, never()).addReceptionist(any(Receptionist.class));
    }

    @Test
    void testAddReceptionist_UsernameTaken() {
        Receptionist receptionist = new Receptionist(0, "existing", "pass", "Name", "555-0203", "email@test.com");
        when(receptionistDAO.isUsernameTaken(anyString(), anyInt())).thenReturn(true);

        boolean result = receptionistService.addReceptionist(receptionist);

        assertFalse(result);
        verify(receptionistDAO, never()).addReceptionist(any(Receptionist.class));
    }

    @Test
    void testUpdateReceptionist_Success() {
        Receptionist receptionist = new Receptionist(1, "user", "pass", "Name", "555-0203", "email@test.com");
        when(receptionistDAO.isUsernameTaken(anyString(), anyInt())).thenReturn(false);
        when(adminDAO.isUsernameTaken(anyString(), anyInt())).thenReturn(false);
        when(receptionistDAO.updateReceptionist(any(Receptionist.class))).thenReturn(true);

        boolean result = receptionistService.updateReceptionist(receptionist);

        assertTrue(result);
        verify(receptionistDAO).updateReceptionist(any(Receptionist.class));
    }

    @Test
    void testUpdateReceptionist_NullReceptionist() {
        boolean result = receptionistService.updateReceptionist(null);

        assertFalse(result);
        verify(receptionistDAO, never()).updateReceptionist(any(Receptionist.class));
    }

    @Test
    void testUpdateReceptionist_InvalidId() {
        Receptionist receptionist = new Receptionist(0, "user", "pass", "Name", "555-0203", "email@test.com");

        boolean result = receptionistService.updateReceptionist(receptionist);

        assertFalse(result);
        verify(receptionistDAO, never()).updateReceptionist(any(Receptionist.class));
    }

    @Test
    void testDeleteReceptionist_Success() {
        when(receptionistDAO.deleteReceptionist(anyInt())).thenReturn(true);

        boolean result = receptionistService.deleteReceptionist(1);

        assertTrue(result);
        verify(receptionistDAO).deleteReceptionist(1);
    }

    @Test
    void testDeleteReceptionist_Failure() {
        when(receptionistDAO.deleteReceptionist(anyInt())).thenReturn(false);

        boolean result = receptionistService.deleteReceptionist(1);

        assertFalse(result);
        verify(receptionistDAO).deleteReceptionist(1);
    }

    // ================= Admin Operations =================

    @Test
    void testGetAllAdmins() {
        Admin admin1 = new Admin(1, "admin", "admin123", "System Administrator", "admin@dentalclinic.com");
        Admin admin2 = new Admin(2, "admin2", "admin456", "Second Admin", "admin2@dentalclinic.com");
        List<Admin> expectedAdmins = Arrays.asList(admin1, admin2);

        when(adminDAO.getAllAdmins()).thenReturn(expectedAdmins);

        List<Admin> result = receptionistService.getAllAdmins();

        assertEquals(2, result.size());
        assertEquals("System Administrator", result.get(0).getName());
        assertEquals("Second Admin", result.get(1).getName());
        verify(adminDAO).getAllAdmins();
    }

    @Test
    void testGetAdminById_Success() {
        Admin admin = new Admin(1, "admin", "admin123", "System Administrator", "admin@dentalclinic.com");
        when(adminDAO.getAdminById(1)).thenReturn(admin);

        Admin result = receptionistService.getAdminById(1);

        assertNotNull(result);
        assertEquals("System Administrator", result.getName());
        verify(adminDAO).getAdminById(1);
    }

    @Test
    void testGetAdminById_NotFound() {
        when(adminDAO.getAdminById(999)).thenReturn(null);

        Admin result = receptionistService.getAdminById(999);

        assertNull(result);
        verify(adminDAO).getAdminById(999);
    }

    @Test
    void testIsAdminUsernameTaken_True() {
        when(adminDAO.isUsernameTaken(anyString(), anyInt())).thenReturn(true);

        boolean result = receptionistService.isAdminUsernameTaken("testuser", 0);

        assertTrue(result);
        verify(adminDAO).isUsernameTaken("testuser", 0);
    }

    @Test
    void testIsAdminUsernameTaken_False() {
        when(adminDAO.isUsernameTaken(anyString(), anyInt())).thenReturn(false);
        when(receptionistDAO.isUsernameTaken(anyString(), anyInt())).thenReturn(false);

        boolean result = receptionistService.isAdminUsernameTaken("testuser", 0);

        assertFalse(result);
        verify(adminDAO).isUsernameTaken("testuser", 0);
        verify(receptionistDAO).isUsernameTaken("testuser", 0);
    }

    @Test
    void testAddAdmin_Success() {
        Admin admin = new Admin(0, "newadmin", "newpass", "New Admin", "newadmin@dentalclinic.com");
        when(adminDAO.isUsernameTaken(anyString(), anyInt())).thenReturn(false);
        when(receptionistDAO.isUsernameTaken(anyString(), anyInt())).thenReturn(false);
        when(adminDAO.addAdmin(any(Admin.class))).thenReturn(true);

        boolean result = receptionistService.addAdmin(admin);

        assertTrue(result);
        verify(adminDAO).addAdmin(any(Admin.class));
    }

    @Test
    void testAddAdmin_NullAdmin() {
        boolean result = receptionistService.addAdmin(null);

        assertFalse(result);
        verify(adminDAO, never()).addAdmin(any(Admin.class));
    }

    @Test
    void testAddAdmin_NullUsername() {
        Admin admin = new Admin(0, null, "pass", "Name", "email@test.com");

        boolean result = receptionistService.addAdmin(admin);

        assertFalse(result);
        verify(adminDAO, never()).addAdmin(any(Admin.class));
    }

    @Test
    void testAddAdmin_NullPassword() {
        Admin admin = new Admin(0, "user", null, "Name", "email@test.com");

        boolean result = receptionistService.addAdmin(admin);

        assertFalse(result);
        verify(adminDAO, never()).addAdmin(any(Admin.class));
    }

    @Test
    void testAddAdmin_UsernameTaken() {
        Admin admin = new Admin(0, "existing", "pass", "Name", "email@test.com");
        when(adminDAO.isUsernameTaken(anyString(), anyInt())).thenReturn(true);

        boolean result = receptionistService.addAdmin(admin);

        assertFalse(result);
        verify(adminDAO, never()).addAdmin(any(Admin.class));
    }

    @Test
    void testUpdateAdmin_Success() {
        Admin admin = new Admin(1, "admin", "pass", "Name", "email@test.com");
        when(adminDAO.isUsernameTaken(anyString(), anyInt())).thenReturn(false);
        when(receptionistDAO.isUsernameTaken(anyString(), anyInt())).thenReturn(false);
        when(adminDAO.updateAdmin(any(Admin.class))).thenReturn(true);

        boolean result = receptionistService.updateAdmin(admin);

        assertTrue(result);
        verify(adminDAO).updateAdmin(any(Admin.class));
    }

    @Test
    void testUpdateAdmin_NullAdmin() {
        boolean result = receptionistService.updateAdmin(null);

        assertFalse(result);
        verify(adminDAO, never()).updateAdmin(any(Admin.class));
    }

    @Test
    void testUpdateAdmin_InvalidId() {
        Admin admin = new Admin(0, "admin", "pass", "Name", "email@test.com");

        boolean result = receptionistService.updateAdmin(admin);

        assertFalse(result);
        verify(adminDAO, never()).updateAdmin(any(Admin.class));
    }

    @Test
    void testDeleteAdmin_Success() {
        when(adminDAO.deleteAdmin(anyInt())).thenReturn(true);

        boolean result = receptionistService.deleteAdmin(1);

        assertTrue(result);
        verify(adminDAO).deleteAdmin(1);
    }

    @Test
    void testDeleteAdmin_Failure() {
        when(adminDAO.deleteAdmin(anyInt())).thenReturn(false);

        boolean result = receptionistService.deleteAdmin(1);

        assertFalse(result);
        verify(adminDAO).deleteAdmin(1);
    }
}
