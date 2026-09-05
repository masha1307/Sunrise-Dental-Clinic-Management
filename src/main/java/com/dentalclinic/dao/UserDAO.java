package com.dentalclinic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dentalclinic.model.User;
import com.dentalclinic.util.DBConnection;

public class UserDAO {

    private AdminDAO adminDAO = new AdminDAO();
    private ReceptionistDAO receptionistDAO = new ReceptionistDAO();

    public boolean login(String username, String password) {
        if (adminDAO.login(username, password)) {
            return true;
        }
        if (receptionistDAO.login(username, password)) {
            return true;
        }

        // Fallback for legacy users table if present
        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        try {
            Connection connection = DBConnection.getConnection();
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, username);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();
                return resultSet.next();
            }
        } catch (SQLException ignored) {
        }
        return false;
    }

    public User getUserByUsername(String username) {
        // Check admins table
        com.dentalclinic.model.Admin admin = adminDAO.getAdminByUsername(username);
        if (admin != null) {
            User user = new User();
            user.setUserId(admin.getAdminId());
            user.setUsername(admin.getUsername());
            user.setPassword(admin.getPassword());
            user.setRole("admin");
            return user;
        }

        // Check receptionists table
        com.dentalclinic.model.Receptionist recep = receptionistDAO.getReceptionistByUsername(username);
        if (recep != null) {
            User user = new User();
            user.setUserId(recep.getReceptionistId());
            user.setUsername(recep.getUsername());
            user.setPassword(recep.getPassword());
            user.setRole("receptionist");
            return user;
        }

    
        String sql = "SELECT * FROM users WHERE username=?";
        try {
            Connection connection = DBConnection.getConnection();
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, username);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    User user = new User();
                    user.setUserId(resultSet.getInt("user_id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRole(resultSet.getString("role"));
                    return user;
                }
            }
        } catch (SQLException ignored) {
        }

        return null;
    }

    // Get all receptionists
    public List<User> getAllReceptionists() {
        List<User> users = new ArrayList<>();
        List<com.dentalclinic.model.Receptionist> list = receptionistDAO.getAllReceptionists();
        for (com.dentalclinic.model.Receptionist r : list) {
            User u = new User();
            u.setUserId(r.getReceptionistId());
            u.setUsername(r.getUsername());
            u.setPassword(r.getPassword());
            u.setRole("receptionist");
            users.add(u);
        }
        return users;
    }

    // Get all admins
    public List<User> getAllAdmins() {
        List<User> users = new ArrayList<>();
        List<com.dentalclinic.model.Admin> list = adminDAO.getAllAdmins();
        for (com.dentalclinic.model.Admin a : list) {
            User u = new User();
            u.setUserId(a.getAdminId());
            u.setUsername(a.getUsername());
            u.setPassword(a.getPassword());
            u.setRole("admin");
            users.add(u);
        }
        return users;
    }

    // Get all users (both admins and receptionists)
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        users.addAll(getAllAdmins());
        users.addAll(getAllReceptionists());
        return users;
    }

    // Get user by ID (checks role context or searches tables)
    public User getUserById(int userId) {
        com.dentalclinic.model.Admin a = adminDAO.getAdminById(userId);
        if (a != null) {
            User u = new User();
            u.setUserId(a.getAdminId());
            u.setUsername(a.getUsername());
            u.setPassword(a.getPassword());
            u.setRole("admin");
            return u;
        }

        com.dentalclinic.model.Receptionist r = receptionistDAO.getReceptionistById(userId);
        if (r != null) {
            User u = new User();
            u.setUserId(r.getReceptionistId());
            u.setUsername(r.getUsername());
            u.setPassword(r.getPassword());
            u.setRole("receptionist");
            return u;
        }

        return null;
    }

    public boolean isUsernameTaken(String username, int excludeUserId) {
        if (username == null || username.trim().isEmpty()) return false;
        return adminDAO.isUsernameTaken(username.trim(), excludeUserId) || receptionistDAO.isUsernameTaken(username.trim(), excludeUserId);
    }

    public boolean addUser(User user) {
        if (user == null) return false;
        if ("admin".equalsIgnoreCase(user.getRole())) {
            com.dentalclinic.model.Admin a = new com.dentalclinic.model.Admin();
            a.setUsername(user.getUsername());
            a.setPassword(user.getPassword());
            a.setName(user.getUsername());
            return adminDAO.addAdmin(a);
        } else {
            com.dentalclinic.model.Receptionist r = new com.dentalclinic.model.Receptionist();
            r.setUsername(user.getUsername());
            r.setPassword(user.getPassword());
            r.setName(user.getUsername());
            return receptionistDAO.addReceptionist(r);
        }
    }

    public boolean updateUser(User user) {
        if (user == null) return false;
        if ("admin".equalsIgnoreCase(user.getRole())) {
            com.dentalclinic.model.Admin a = new com.dentalclinic.model.Admin();
            a.setAdminId(user.getUserId());
            a.setUsername(user.getUsername());
            a.setPassword(user.getPassword());
            a.setName(user.getUsername());
            return adminDAO.updateAdmin(a);
        } else {
            com.dentalclinic.model.Receptionist r = new com.dentalclinic.model.Receptionist();
            r.setReceptionistId(user.getUserId());
            r.setUsername(user.getUsername());
            r.setPassword(user.getPassword());
            r.setName(user.getUsername());
            return receptionistDAO.updateReceptionist(r);
        }
    }

    public boolean deleteUser(int userId, String role) {
        if ("admin".equalsIgnoreCase(role)) {
            return adminDAO.deleteAdmin(userId);
        } else {
            return receptionistDAO.deleteReceptionist(userId);
        }
    }

    public boolean deleteUser(int userId) {
        boolean deleted = receptionistDAO.deleteReceptionist(userId);
        if (!deleted) {
            deleted = adminDAO.deleteAdmin(userId);
        }
        return deleted;
    }
}
