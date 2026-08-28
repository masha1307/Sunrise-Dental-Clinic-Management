package com.dentalclinic.service;

import com.dentalclinic.dao.UserDAO;

public class LoginService {

    private UserDAO userDAO;

    public LoginService() {
        userDAO = new UserDAO();
    }


    public boolean login(String username, String password) {

        // Validate empty fields
        if (username == null || username.isEmpty()) {
            return false;
        }

        if (password == null || password.isEmpty()) {
            return false;
        }


        // Check user from database
        return userDAO.login(username, password);
    }

    // Return full user object (includes role)
    public com.dentalclinic.model.User getUserByUsername(String username) {
        return new UserDAO().getUserByUsername(username);
    }

}