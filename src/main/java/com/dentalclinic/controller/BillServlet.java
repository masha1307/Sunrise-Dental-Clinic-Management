package com.dentalclinic.controller;

import java.io.IOException;

import com.dentalclinic.model.Bill;
import com.dentalclinic.service.BillService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/bill")
public class BillServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private BillService billService;

    @Override
    public void init() throws ServletException {
        billService = new BillService();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));

        double consultationFee = Double.parseDouble(request.getParameter("consultationFee"));

        double treatmentFee = Double.parseDouble(request.getParameter("treatmentFee"));

        double discount = Double.parseDouble(request.getParameter("discount"));

        // Calculate total bill
        double totalAmount = billService.calculateBill(
                consultationFee,
                treatmentFee,
                discount);

        Bill bill = new Bill();

        bill.setAppointmentId(appointmentId);
        bill.setConsultationFee(consultationFee);
        bill.setTreatmentFee(treatmentFee);
        bill.setDiscount(discount);
        bill.setTotalAmount(totalAmount);

        boolean success = billService.saveBill(bill);

        if (success) {

            request.setAttribute("bill", bill);
            request.setAttribute("successMessage",
                    "Bill generated successfully.");

        } else {

            request.setAttribute("errorMessage",
                    "Failed to generate bill.");

        }

        request.getRequestDispatcher("jsp/bill.jsp")
                .forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect("jsp/bill.jsp");
    }
}