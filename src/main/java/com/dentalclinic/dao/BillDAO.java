package com.dentalclinic.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.dentalclinic.model.Bill;
import com.dentalclinic.util.DBConnection;

public class BillDAO {

    public boolean saveBill(Bill bill) {

        String sql = "INSERT INTO bills "
                + "(appointment_id, consultation_fee, treatment_fee, total_amount) "
                + "VALUES (?, ?, ?, ?)";

        try {

            Connection connection = DBConnection.getConnection();

            if (connection == null) {
                System.err.println("ERROR: Database connection is null");
                return false;
            }

            try (PreparedStatement statement =
                         connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                statement.setInt(1, bill.getAppointmentId());
                statement.setDouble(2, bill.getConsultationFee());
                statement.setDouble(3, bill.getTreatmentFee());
                statement.setDouble(4, bill.getTotalAmount());

                int rows = statement.executeUpdate();

                if (rows > 0) {
                    try (ResultSet keys = statement.getGeneratedKeys()) {
                        if (keys.next()) {
                            bill.setBillId(keys.getInt(1));
                        }
                    }
                }

                System.out.println("Bill saved. Rows affected: " + rows);

                return rows > 0;
            }

        } catch (SQLException e) {
            System.err.println("Save Bill Error: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
        }

        return false;
    }

    public Bill getBillByAppointmentId(int appointmentId) {

        String sql = "SELECT * FROM bills WHERE appointment_id = ? ORDER BY bill_id DESC LIMIT 1";

        try {

            Connection connection = DBConnection.getConnection();

            if (connection == null) {
                System.err.println("ERROR: Database connection is null");
                return null;
            }

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setInt(1, appointmentId);

                try (ResultSet rs = statement.executeQuery()) {

                    if (rs.next()) {
                        Bill bill = new Bill();
                        bill.setBillId(rs.getInt("bill_id"));
                        bill.setAppointmentId(rs.getInt("appointment_id"));
                        bill.setConsultationFee(rs.getDouble("consultation_fee"));
                        bill.setTreatmentFee(rs.getDouble("treatment_fee"));
                        bill.setTotalAmount(rs.getDouble("total_amount"));
                        return bill;
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Get Bill Error: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public List<Bill> getAllBills() {
        return new ArrayList<>();
    }

}