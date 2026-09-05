package com.dentalclinic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dentalclinic.model.Receptionist;
import com.dentalclinic.util.DBConnection;

public class ReceptionistDAO {

    public boolean login(String username, String password) {
        String sql = "SELECT * FROM receptionists WHERE username = ? AND password = ?";

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
            System.err.println("ReceptionistDAO.login (receptionists table): " + e.getMessage());
        }

        // Fallback: Check users table
        String fallbackSql = "SELECT * FROM users WHERE username = ? AND password = ? AND LOWER(role) = 'receptionist'";
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

    public Receptionist getReceptionistByUsername(String username) {
        String sql = "SELECT * FROM receptionists WHERE username = ?";

        try {
            Connection connection = DBConnection.getConnection();
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, username);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    Receptionist receptionist = new Receptionist();
                    receptionist.setReceptionistId(resultSet.getInt("receptionist_id"));
                    receptionist.setUsername(resultSet.getString("username"));
                    receptionist.setPassword(resultSet.getString("password"));
                    receptionist.setName(resultSet.getString("name"));
                    receptionist.setContactNumber(resultSet.getString("contact_number"));
                    receptionist.setEmail(resultSet.getString("email"));
                    return receptionist;
                }
            }
        } catch (SQLException e) {
            System.err.println("ReceptionistDAO.getReceptionistByUsername (receptionists table): " + e.getMessage());
        }

        // Fallback: Check users table
        String fallbackSql = "SELECT * FROM users WHERE username = ? AND LOWER(role) = 'receptionist'";
        try {
            Connection connection = DBConnection.getConnection();
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement(fallbackSql);
                statement.setString(1, username);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    Receptionist receptionist = new Receptionist();
                    receptionist.setReceptionistId(resultSet.getInt("user_id"));
                    receptionist.setUsername(resultSet.getString("username"));
                    receptionist.setPassword(resultSet.getString("password"));
                    receptionist.setName(resultSet.getString("username"));
                    receptionist.setContactNumber("555-0201");
                    receptionist.setEmail(resultSet.getString("username") + "@dentalclinic.com");
                    return receptionist;
                }
            }
        } catch (SQLException ignored) {
        }

        return null;
    }

    public Receptionist getReceptionistById(int receptionistId) {
        String sql = "SELECT * FROM receptionists WHERE receptionist_id = ?";

        try {
            Connection connection = DBConnection.getConnection();
            if (connection == null) return null;

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, receptionistId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Receptionist receptionist = new Receptionist();
                receptionist.setReceptionistId(resultSet.getInt("receptionist_id"));
                receptionist.setUsername(resultSet.getString("username"));
                receptionist.setPassword(resultSet.getString("password"));
                receptionist.setName(resultSet.getString("name"));
                receptionist.setContactNumber(resultSet.getString("contact_number"));
                receptionist.setEmail(resultSet.getString("email"));
                return receptionist;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Receptionist> getAllReceptionists() {
        List<Receptionist> list = new ArrayList<>();
        String sql = "SELECT * FROM receptionists ORDER BY receptionist_id DESC";

        try {
            Connection connection = DBConnection.getConnection();
            if (connection == null) return list;

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Receptionist receptionist = new Receptionist();
                receptionist.setReceptionistId(resultSet.getInt("receptionist_id"));
                receptionist.setUsername(resultSet.getString("username"));
                receptionist.setPassword(resultSet.getString("password"));
                receptionist.setName(resultSet.getString("name"));
                receptionist.setContactNumber(resultSet.getString("contact_number"));
                receptionist.setEmail(resultSet.getString("email"));
                list.add(receptionist);
            }
        } catch (SQLException e) {
            System.err.println("ReceptionistDAO.getAllReceptionists (receptionists table): " + e.getMessage());
        }

        // Fallback: Check users table for receptionists
        if (list.isEmpty()) {
            String fallbackSql = "SELECT * FROM users WHERE LOWER(role) = 'receptionist' ORDER BY user_id DESC";
            try {
                Connection connection = DBConnection.getConnection();
                if (connection != null) {
                    PreparedStatement statement = connection.prepareStatement(fallbackSql);
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        Receptionist receptionist = new Receptionist();
                        receptionist.setReceptionistId(resultSet.getInt("user_id"));
                        receptionist.setUsername(resultSet.getString("username"));
                        receptionist.setPassword(resultSet.getString("password"));
                        receptionist.setName(resultSet.getString("username"));
                        receptionist.setContactNumber("555-0201");
                        receptionist.setEmail(resultSet.getString("username") + "@dentalclinic.com");
                        list.add(receptionist);
                    }
                }
            } catch (SQLException ignored) {
            }
        }

        return list;
    }

    public boolean isUsernameTaken(String username, int excludeReceptionistId) {
        String sql = "SELECT COUNT(*) FROM receptionists WHERE username = ? AND receptionist_id != ?";

        try {
            Connection connection = DBConnection.getConnection();
            if (connection == null) return false;

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setInt(2, excludeReceptionistId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addReceptionist(Receptionist receptionist) {
        String sql = "INSERT INTO receptionists (username, password, name, contact_number, email) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection connection = DBConnection.getConnection();
            if (connection == null) return false;

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, receptionist.getUsername());
            statement.setString(2, receptionist.getPassword());
            statement.setString(3, receptionist.getName());
            statement.setString(4, receptionist.getContactNumber());
            statement.setString(5, receptionist.getEmail());

            int rows = statement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateReceptionist(Receptionist receptionist) {
        boolean updatePassword = receptionist.getPassword() != null && !receptionist.getPassword().trim().isEmpty();
        String sql = updatePassword
                ? "UPDATE receptionists SET username = ?, password = ?, name = ?, contact_number = ?, email = ? WHERE receptionist_id = ?"
                : "UPDATE receptionists SET username = ?, name = ?, contact_number = ?, email = ? WHERE receptionist_id = ?";

        try {
            Connection connection = DBConnection.getConnection();
            if (connection == null) return false;

            PreparedStatement statement = connection.prepareStatement(sql);
            if (updatePassword) {
                statement.setString(1, receptionist.getUsername());
                statement.setString(2, receptionist.getPassword());
                statement.setString(3, receptionist.getName());
                statement.setString(4, receptionist.getContactNumber());
                statement.setString(5, receptionist.getEmail());
                statement.setInt(6, receptionist.getReceptionistId());
            } else {
                statement.setString(1, receptionist.getUsername());
                statement.setString(2, receptionist.getName());
                statement.setString(3, receptionist.getContactNumber());
                statement.setString(4, receptionist.getEmail());
                statement.setInt(5, receptionist.getReceptionistId());
            }

            int rows = statement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteReceptionist(int receptionistId) {
        String sql = "DELETE FROM receptionists WHERE receptionist_id = ?";

        try {
            Connection connection = DBConnection.getConnection();
            if (connection == null) return false;

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, receptionistId);

            int rows = statement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
