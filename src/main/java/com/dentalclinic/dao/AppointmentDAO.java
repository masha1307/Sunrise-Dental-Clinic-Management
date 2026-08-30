package com.dentalclinic.dao;

import com.dentalclinic.model.Appointment;
import com.dentalclinic.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AppointmentDAO {

    // ==========================
    // GENERATE APPOINTMENT NUMBER
    // Example: APT-20260806-A1B2C3
    // ==========================
    private String generateAppointmentNumber() {

        String currentDate = LocalDate.now()
                .format(DateTimeFormatter.BASIC_ISO_DATE);

        String randomCode = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 6)
                .toUpperCase();

        return "APT-" + currentDate + "-" + randomCode;
    }


    // ==========================
    // ADD APPOINTMENT
    // ==========================
    public int addAppointment(Appointment appointment) {

        String sql =
                "INSERT INTO appointments "
                + "(appointment_number, patient_id, dentist_id, treatment_id, "
                + "appointment_date, appointment_time) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        String appointmentNumber = generateAppointmentNumber();

        try {

            Connection connection = DBConnection.getConnection();

            if (connection == null) {
                System.err.println("ERROR: Database connection is null");
                return -1;
            }

            try (PreparedStatement statement =
                         connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, appointmentNumber);
                statement.setInt(2, appointment.getPatientId());
                statement.setInt(3, appointment.getDentistId());
                statement.setInt(4, appointment.getTreatmentId());
                statement.setString(
                        5,
                        appointment.getAppointmentDate()
                );
                statement.setString(
                        6,
                        appointment.getAppointmentTime()
                );

                int rows = statement.executeUpdate();

                System.out.println(
                        "Appointment Number: " + appointmentNumber
                );

                System.out.println(
                        "Inserted Rows: " + rows
                );

                if (rows > 0) {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int appointmentId = generatedKeys.getInt(1);
                        appointment.setAppointmentId(appointmentId);
                        System.out.println("Generated Appointment ID: " + appointmentId);
                        return appointmentId;
                    }
                }
                return -1;
            }

        } catch (SQLException e) {

            System.err.println(
                    "Add Appointment Error: " + e.getMessage()
            );

            System.err.println(
                    "SQL State: " + e.getSQLState()
            );

            System.err.println(
                    "Error Code: " + e.getErrorCode()
            );

            e.printStackTrace();
        }

        return -1;
    }


    // ==========================
    // UPDATE APPOINTMENT
    // ==========================
    public boolean updateAppointment(Appointment appointment) {

        String sql =
                "UPDATE appointments SET "
                + "patient_id = ?, "
                + "dentist_id = ?, "
                + "treatment_id = ?, "
                + "appointment_date = ?, "
                + "appointment_time = ? "
                + "WHERE appointment_id = ?";

        try {

            Connection connection = DBConnection.getConnection();

            if (connection == null) {
                System.err.println("ERROR: Database connection is null");
                return false;
            }

            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        appointment.getPatientId()
                );

                statement.setInt(
                        2,
                        appointment.getDentistId()
                );

                statement.setInt(
                        3,
                        appointment.getTreatmentId()
                );

                statement.setString(
                        4,
                        appointment.getAppointmentDate()
                );

                statement.setString(
                        5,
                        appointment.getAppointmentTime()
                );

                statement.setInt(
                        6,
                        appointment.getAppointmentId()
                );

                int rows = statement.executeUpdate();

                System.out.println(
                        "Updated Rows: " + rows
                );

                return rows > 0;
            }

        } catch (SQLException e) {

            System.err.println(
                    "Update Appointment Error: " + e.getMessage()
            );

            System.err.println(
                    "SQL State: " + e.getSQLState()
            );

            System.err.println(
                    "Error Code: " + e.getErrorCode()
            );

            e.printStackTrace();
        }

        return false;
    }


    // ==========================
    // DELETE APPOINTMENT
    // ==========================
    public boolean deleteAppointment(int appointmentId) {

        String sql =
                "DELETE FROM appointments "
                + "WHERE appointment_id = ?";

        try {

            Connection connection = DBConnection.getConnection();

            if (connection == null) {
                System.err.println("ERROR: Database connection is null");
                return false;
            }

            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(1, appointmentId);

                int rows = statement.executeUpdate();

                System.out.println(
                        "Deleted Rows: " + rows
                );

                return rows > 0;
            }

        } catch (SQLException e) {

            System.err.println(
                    "Delete Appointment Error: " + e.getMessage()
            );

            System.err.println(
                    "SQL State: " + e.getSQLState()
            );

            System.err.println(
                    "Error Code: " + e.getErrorCode()
            );

            e.printStackTrace();
        }

        return false;
    }


    // ==========================
    // GET APPOINTMENT BY ID
    // ==========================
    public Appointment getAppointmentById(int appointmentId) {

        String sql =
                "SELECT a.*, p.patient_name, d.dentist_name, t.treatment_name "
                + "FROM appointments a "
                + "LEFT JOIN patients p ON a.patient_id = p.patient_id "
                + "LEFT JOIN dentists d ON a.dentist_id = d.dentist_id "
                + "LEFT JOIN treatments t ON a.treatment_id = t.treatment_id "
                + "WHERE a.appointment_id = ?";

        try {

            Connection connection = DBConnection.getConnection();

            if (connection == null) {
                System.err.println("ERROR: Database connection is null");
                return null;
            }

            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(1, appointmentId);

                try (ResultSet resultSet =
                             statement.executeQuery()) {

                    if (resultSet.next()) {
                        return mapAppointment(resultSet);
                    }
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Get Appointment Error: " + e.getMessage()
            );

            System.err.println(
                    "SQL State: " + e.getSQLState()
            );

            System.err.println(
                    "Error Code: " + e.getErrorCode()
            );

            e.printStackTrace();
        }

        return null;
    }


    // ==========================
    // GET ALL APPOINTMENTS
    // ==========================
    public List<Appointment> getAllAppointments() {

        List<Appointment> appointments = new ArrayList<>();

        String sql =
                "SELECT * FROM appointments "
                + "ORDER BY appointment_id DESC";

        try {

            Connection connection = DBConnection.getConnection();

            if (connection == null) {
                System.err.println("ERROR: Database connection is null");
                return appointments;
            }

            try (PreparedStatement statement =
                         connection.prepareStatement(sql);

                 ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    Appointment appointment =
                            mapAppointment(resultSet);

                    appointments.add(appointment);
                }
            }

            System.out.println(
                    "Retrieved "
                    + appointments.size()
                    + " appointments from database"
            );

        } catch (SQLException e) {

            System.err.println(
                    "Get All Appointments Error: " + e.getMessage()
            );

            System.err.println(
                    "SQL State: " + e.getSQLState()
            );

            System.err.println(
                    "Error Code: " + e.getErrorCode()
            );

            e.printStackTrace();
        }

        return appointments;
    }


    // ==========================
    // GET APPOINTMENTS BY DATE
    // ==========================
    public List<Appointment> getAppointmentsByDate(String date) {
        List<Appointment> appointments = new ArrayList<>();

        String sql =
                "SELECT a.*, p.patient_name, d.dentist_name, t.treatment_name "
                + "FROM appointments a "
                + "LEFT JOIN patients p ON a.patient_id = p.patient_id "
                + "LEFT JOIN dentists d ON a.dentist_id = d.dentist_id "
                + "LEFT JOIN treatments t ON a.treatment_id = t.treatment_id "
                + "WHERE a.appointment_date = ? "
                + "ORDER BY a.appointment_time ASC";

        try {
            Connection connection = DBConnection.getConnection();

            if (connection == null) {
                System.err.println("ERROR: Database connection is null");
                return appointments;
            }

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, date);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Appointment appt = mapAppointment(resultSet);
                        appointments.add(appt);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Get Appointments By Date Error: " + e.getMessage());
            e.printStackTrace();
        }

        return appointments;
    }

    // ==========================
    // SEARCH APPOINTMENTS
    // ==========================
    public List<Appointment> searchAppointments(String keyword) {

        List<Appointment> appointments = new ArrayList<>();

        String sql =
                "SELECT a.*, p.patient_name, d.dentist_name, t.treatment_name "
                + "FROM appointments a "
                + "LEFT JOIN patients p ON a.patient_id = p.patient_id "
                + "LEFT JOIN dentists d ON a.dentist_id = d.dentist_id "
                + "LEFT JOIN treatments t ON a.treatment_id = t.treatment_id "
                + "WHERE a.appointment_number LIKE ? "
                + "OR CAST(a.appointment_id AS CHAR) LIKE ? "
                + "OR CAST(a.patient_id AS CHAR) LIKE ? "
                + "OR p.patient_name LIKE ? "
                + "ORDER BY a.appointment_id DESC";

        try {

            Connection connection = DBConnection.getConnection();

            if (connection == null) {
                System.err.println("ERROR: Database connection is null");
                return appointments;
            }

            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                String searchKeyword = "%" + keyword + "%";

                statement.setString(1, searchKeyword);
                statement.setString(2, searchKeyword);
                statement.setString(3, searchKeyword);
                statement.setString(4, searchKeyword);

                try (ResultSet resultSet =
                             statement.executeQuery()) {

                    while (resultSet.next()) {

                        Appointment appointment =
                                mapAppointment(resultSet);

                        appointments.add(appointment);
                    }
                }
            }

            System.out.println(
                    "Search Results: "
                    + appointments.size()
            );

        } catch (SQLException e) {

            System.err.println(
                    "Search Appointment Error: " + e.getMessage()
            );

            System.err.println(
                    "SQL State: " + e.getSQLState()
            );

            System.err.println(
                    "Error Code: " + e.getErrorCode()
            );

            e.printStackTrace();
        }

        return appointments;
    }


    // ==========================
    // MAP RESULTSET TO MODEL
    // ==========================
    private Appointment mapAppointment(
            ResultSet resultSet
    ) throws SQLException {

        Appointment appointment = new Appointment();

        appointment.setAppointmentId(
                resultSet.getInt("appointment_id")
        );

        appointment.setPatientId(
                resultSet.getInt("patient_id")
        );

        appointment.setDentistId(
                resultSet.getInt("dentist_id")
        );

        appointment.setTreatmentId(
                resultSet.getInt("treatment_id")
        );

        appointment.setAppointmentDate(
                resultSet.getString("appointment_date")
        );

        appointment.setAppointmentTime(
                resultSet.getString("appointment_time")
        );

        appointment.setPatientName(
                resultSet.getString("patient_name")
        );

        appointment.setDentistName(
                resultSet.getString("dentist_name")
        );

        appointment.setTreatmentName(
                resultSet.getString("treatment_name")
        );

        return appointment;
    }
}