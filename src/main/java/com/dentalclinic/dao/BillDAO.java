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
                         connection.prepareStatement(sql)) {

                statement.setInt(1, bill.getAppointmentId());
                statement.setDouble(2, bill.getConsultationFee());
                statement.setDouble(3, bill.getTreatmentFee());
                statement.setDouble(4, bill.getTotalAmount());

                int rows = statement.executeUpdate();

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

        return null;
    }

    public List<Bill> getAllBills() {

        return new ArrayList<>();
    }

}