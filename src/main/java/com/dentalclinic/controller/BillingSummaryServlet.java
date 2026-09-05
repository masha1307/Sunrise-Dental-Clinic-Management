package com.dentalclinic.controller;

import com.dentalclinic.model.Bill;
import com.dentalclinic.util.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/billingSummary")
public class BillingSummaryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String date = request.getParameter("date");
        List<Bill> bills = new ArrayList<>();

        if (date != null && !date.isEmpty()) {

            String sql = "SELECT b.*, a.appointment_date, p.patient_name, a.appointment_number, a.appointment_time "
                    + "FROM bills b "
                    + "LEFT JOIN appointments a ON b.appointment_id = a.appointment_id "
                    + "LEFT JOIN patients p ON a.patient_id = p.patient_id "
                    + "WHERE a.appointment_date = ? "
                    + "ORDER BY a.appointment_time ASC";

            try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, date);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Bill bill = new Bill();
                        bill.setBillId(rs.getInt("bill_id"));
                        bill.setAppointmentId(rs.getInt("appointment_id"));
                        bill.setConsultationFee(rs.getDouble("consultation_fee"));
                        bill.setTreatmentFee(rs.getDouble("treatment_fee"));
                        bill.setTotalAmount(rs.getDouble("total_amount"));
                        try { bill.setAppointmentDate(rs.getString("appointment_date")); } catch (Exception ignored) {}
                        try { bill.setPatientName(rs.getString("patient_name")); } catch (Exception ignored) {}
                        try { bill.setAppointmentNumber(rs.getString("appointment_number")); } catch (Exception ignored) {}
                        bills.add(bill);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        request.setAttribute("bills", bills);
        request.setAttribute("selectedDate", date);
        request.getRequestDispatcher("/jsp/billing_summary.jsp").forward(request, response);
    }
}