package com.dentalclinic.service;

import com.dentalclinic.dao.AdminDAO;
import com.dentalclinic.dao.ReceptionistDAO;
import com.dentalclinic.model.Admin;
import com.dentalclinic.model.Receptionist;
import com.dentalclinic.model.User;

public class LoginService {

    private AdminDAO adminDAO;
    private ReceptionistDAO receptionistDAO;
    public LoginService() {
        this.adminDAO = new AdminDAO();
        this.receptionistDAO = new ReceptionistDAO();
    }
    public LoginService(AdminDAO adminDAO, ReceptionistDAO receptionistDAO) {
        this.adminDAO = adminDAO;
        this.receptionistDAO = receptionistDAO;
    }
    public boolean login(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        if (password == null || password.trim().isEmpty()) {
            return false;
        }
        String trimmedUser = username.trim();
        String trimmedPass = password.trim();

        // 1. Check Admins table
        if (adminDAO.login(trimmedUser, trimmedPass)) {
            return true;
        }
        // 2. Check Receptionists table
        if (receptionistDAO.login(trimmedUser, trimmedPass)) {
            return true;
        }
        return false;
    }
    // Return unified user object with role for session management
    public User getUserByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return null;
        }

        String trimmedUser = username.trim();

        // 1. Check Admin
        Admin admin = adminDAO.getAdminByUsername(trimmedUser);
        if (admin != null) {
            User user = new User();
            user.setUserId(admin.getAdminId());
            user.setUsername(admin.getUsername());
            user.setPassword(admin.getPassword());
            user.setRole("admin");
            return user;
        }

        // 2. Check Receptionist
        Receptionist receptionist = receptionistDAO.getReceptionistByUsername(trimmedUser);
        if (receptionist != null) {
            User user = new User();
            user.setUserId(receptionist.getReceptionistId());
            user.setUsername(receptionist.getUsername());
            user.setPassword(receptionist.getPassword());
            user.setRole("receptionist");
            return user;
        }

        return null;
    }
}