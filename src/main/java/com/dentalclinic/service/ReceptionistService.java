package com.dentalclinic.service;

import java.util.List;

import com.dentalclinic.dao.AdminDAO;
import com.dentalclinic.dao.ReceptionistDAO;
import com.dentalclinic.model.Admin;
import com.dentalclinic.model.Receptionist;

public class ReceptionistService {

    private ReceptionistDAO receptionistDAO;
    private AdminDAO adminDAO;

    public ReceptionistService() {
        this.receptionistDAO = new ReceptionistDAO();
        this.adminDAO = new AdminDAO();
    }

    public ReceptionistService(ReceptionistDAO receptionistDAO, AdminDAO adminDAO) {
        this.receptionistDAO = receptionistDAO;
        this.adminDAO = adminDAO;
    }

    // ================= Receptionist Operations =================
    public List<Receptionist> getAllReceptionists() {
        return receptionistDAO.getAllReceptionists();
    }

    public Receptionist getReceptionistById(int id) {
        return receptionistDAO.getReceptionistById(id);
    }

    public boolean isReceptionistUsernameTaken(String username, int excludeId) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        return receptionistDAO.isUsernameTaken(username.trim(), excludeId) || adminDAO.isUsernameTaken(username.trim(), 0);
    }

    public boolean addReceptionist(Receptionist receptionist) {
        if (receptionist == null || receptionist.getUsername() == null || receptionist.getUsername().trim().isEmpty()) {
            return false;
        }
        if (receptionist.getPassword() == null || receptionist.getPassword().trim().isEmpty()) {
            return false;
        }
        if (isReceptionistUsernameTaken(receptionist.getUsername().trim(), 0)) {
            return false;
        }

        receptionist.setUsername(receptionist.getUsername().trim());
        receptionist.setPassword(receptionist.getPassword().trim());
        if (receptionist.getName() != null) receptionist.setName(receptionist.getName().trim());
        if (receptionist.getContactNumber() != null) receptionist.setContactNumber(receptionist.getContactNumber().trim());
        if (receptionist.getEmail() != null) receptionist.setEmail(receptionist.getEmail().trim());

        return receptionistDAO.addReceptionist(receptionist);
    }

    public boolean updateReceptionist(Receptionist receptionist) {
        if (receptionist == null || receptionist.getReceptionistId() <= 0) {
            return false;
        }
        if (receptionist.getUsername() == null || receptionist.getUsername().trim().isEmpty()) {
            return false;
        }
        if (isReceptionistUsernameTaken(receptionist.getUsername().trim(), receptionist.getReceptionistId())) {
            return false;
        }

        receptionist.setUsername(receptionist.getUsername().trim());
        if (receptionist.getPassword() != null) receptionist.setPassword(receptionist.getPassword().trim());
        if (receptionist.getName() != null) receptionist.setName(receptionist.getName().trim());
        if (receptionist.getContactNumber() != null) receptionist.setContactNumber(receptionist.getContactNumber().trim());
        if (receptionist.getEmail() != null) receptionist.setEmail(receptionist.getEmail().trim());

        return receptionistDAO.updateReceptionist(receptionist);
    }

    public boolean deleteReceptionist(int id) {
        return receptionistDAO.deleteReceptionist(id);
    }

    // ================= Admin Operations =================
    public List<Admin> getAllAdmins() {
        return adminDAO.getAllAdmins();
    }

    public Admin getAdminById(int id) {
        return adminDAO.getAdminById(id);
    }

    public boolean isAdminUsernameTaken(String username, int excludeId) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        return adminDAO.isUsernameTaken(username.trim(), excludeId) || receptionistDAO.isUsernameTaken(username.trim(), 0);
    }

    public boolean addAdmin(Admin admin) {
        if (admin == null || admin.getUsername() == null || admin.getUsername().trim().isEmpty()) {
            return false;
        }
        if (admin.getPassword() == null || admin.getPassword().trim().isEmpty()) {
            return false;
        }
        if (isAdminUsernameTaken(admin.getUsername().trim(), 0)) {
            return false;
        }

        admin.setUsername(admin.getUsername().trim());
        admin.setPassword(admin.getPassword().trim());
        if (admin.getName() != null) admin.setName(admin.getName().trim());
        if (admin.getEmail() != null) admin.setEmail(admin.getEmail().trim());

        return adminDAO.addAdmin(admin);
    }

    public boolean updateAdmin(Admin admin) {
        if (admin == null || admin.getAdminId() <= 0) {
            return false;
        }
        if (admin.getUsername() == null || admin.getUsername().trim().isEmpty()) {
            return false;
        }
        if (isAdminUsernameTaken(admin.getUsername().trim(), admin.getAdminId())) {
            return false;
        }

        admin.setUsername(admin.getUsername().trim());
        if (admin.getPassword() != null) admin.setPassword(admin.getPassword().trim());
        if (admin.getName() != null) admin.setName(admin.getName().trim());
        if (admin.getEmail() != null) admin.setEmail(admin.getEmail().trim());

        return adminDAO.updateAdmin(admin);
    }

    public boolean deleteAdmin(int id) {
        return adminDAO.deleteAdmin(id);
    }
}
