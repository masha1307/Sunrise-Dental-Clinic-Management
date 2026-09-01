package com.dentalclinic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dentalclinic.model.Admin;
import com.dentalclinic.util.DBConnection;

public class AdminDAO {

    public boolean login(String username, String password) {
        String sql = "SELECT * FROM admins WHERE username = ? AND password = ?";

        try {
            Connection connection = DBConnection.getConnection();
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, username);
                statement.setString(2, password);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("AdminDAO.login (admins table): " + e.getMessage());
        }

        // Fallback: Check users table
        String fallbackSql = "SELECT * FROM users WHERE username = ? AND password = ? AND LOWER(role) = 'admin'";
        try {
            Connection connection = DBConnection.getConnection();
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement(fallbackSql);
                statement.setString(1, username);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();
                return resultSet.next();
            }
        } catch (SQLException ignored) {
        }

        return false;
    }

    public Admin getAdminByUsername(String username) {
        String sql = "SELECT * FROM admins WHERE username = ?";

        try {
            Connection connection = DBConnection.getConnection();
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, username);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    Admin admin = new Admin();
                    admin.setAdminId(resultSet.getInt("admin_id"));
                    admin.setUsername(resultSet.getString("username"));
                    admin.setPassword(resultSet.getString("password"));
                    admin.setName(resultSet.getString("name"));
                    admin.setEmail(resultSet.getString("email"));
                    return admin;
                }
            }
        } catch (SQLException e) {
            System.err.println("AdminDAO.getAdminByUsername (admins table): " + e.getMessage());
        }

        // Fallback: Check users table
        String fallbackSql = "SELECT * FROM users WHERE username = ? AND LOWER(role) = 'admin'";
        try {
            Connection connection = DBConnection.getConnection();
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement(fallbackSql);
                statement.setString(1, username);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    Admin admin = new Admin();
                    admin.setAdminId(resultSet.getInt("user_id"));
                    admin.setUsername(resultSet.getString("username"));
                    admin.setPassword(resultSet.getString("password"));
                    admin.setName(resultSet.getString("username"));
                    admin.setEmail(resultSet.getString("username") + "@dentalclinic.com");
                    return admin;
                }
            }
        } catch (SQLException ignored) {
        }

        return null;
    }

    public Admin getAdminById(int adminId) {
        String sql = "SELECT * FROM admins WHERE admin_id = ?";

        try {
            Connection connection = DBConnection.getConnection();
            if (connection == null) return null;

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, adminId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Admin admin = new Admin();
                admin.setAdminId(resultSet.getInt("admin_id"));
                admin.setUsername(resultSet.getString("username"));
                admin.setPassword(resultSet.getString("password"));
                admin.setName(resultSet.getString("name"));
                admin.setEmail(resultSet.getString("email"));
                return admin;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        String sql = "SELECT * FROM admins ORDER BY admin_id DESC";

        try {
            Connection connection = DBConnection.getConnection();
            if (connection == null) return admins;

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Admin admin = new Admin();
                admin.setAdminId(resultSet.getInt("admin_id"));
                admin.setUsername(resultSet.getString("username"));
                admin.setPassword(resultSet.getString("password"));
                admin.setName(resultSet.getString("name"));
                admin.setEmail(resultSet.getString("email"));
                admins.add(admin);
            }
        } catch (SQLException e) {
            System.err.println("AdminDAO.getAllAdmins (admins table): " + e.getMessage());
        }

        // If empty, check users table for admins
        if (admins.isEmpty()) {
            String fallbackSql = "SELECT * FROM users WHERE LOWER(role) = 'admin' ORDER BY user_id DESC";
            try {
                Connection connection = DBConnection.getConnection();
                if (connection != null) {
                    PreparedStatement statement = connection.prepareStatement(fallbackSql);
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        Admin admin = new Admin();
                        admin.setAdminId(resultSet.getInt("user_id"));
                        admin.setUsername(resultSet.getString("username"));
                        admin.setPassword(resultSet.getString("password"));
                        admin.setName(resultSet.getString("username"));
                        admin.setEmail(resultSet.getString("username") + "@dentalclinic.com");
                        admins.add(admin);
                    }
                }
            } catch (SQLException ignored) {
            }
        }

        return admins;
    }

    public boolean isUsernameTaken(String username, int excludeAdminId) {
        String sql = "SELECT COUNT(*) FROM admins WHERE username = ? AND admin_id != ?";

        try {
            Connection connection = DBConnection.getConnection();
            if (connection == null) return false;

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setInt(2, excludeAdminId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addAdmin(Admin admin) {
        String sql = "INSERT INTO admins (username, password, name, email) VALUES (?, ?, ?, ?)";

        try {
            Connection connection = DBConnection.getConnection();
            if (connection == null) return false;

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, admin.getUsername());
            statement.setString(2, admin.getPassword());
            statement.setString(3, admin.getName());
            statement.setString(4, admin.getEmail());

            int rows = statement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAdmin(Admin admin) {
        boolean updatePassword = admin.getPassword() != null && !admin.getPassword().trim().isEmpty();
        String sql = updatePassword
                ? "UPDATE admins SET username = ?, password = ?, name = ?, email = ? WHERE admin_id = ?"
                : "UPDATE admins SET username = ?, name = ?, email = ? WHERE admin_id = ?";

        try {
            Connection connection = DBConnection.getConnection();
            if (connection == null) return false;

            PreparedStatement statement = connection.prepareStatement(sql);
            if (updatePassword) {
                statement.setString(1, admin.getUsername());
                statement.setString(2, admin.getPassword());
                statement.setString(3, admin.getName());
                statement.setString(4, admin.getEmail());
                statement.setInt(5, admin.getAdminId());
            } else {
                statement.setString(1, admin.getUsername());
                statement.setString(2, admin.getName());
                statement.setString(3, admin.getEmail());
                statement.setInt(4, admin.getAdminId());
            }

            int rows = statement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAdmin(int adminId) {
        String sql = "DELETE FROM admins WHERE admin_id = ?";

        try {
            Connection connection = DBConnection.getConnection();
            if (connection == null) return false;

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, adminId);

            int rows = statement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
