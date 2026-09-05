package com.dentalclinic.controller;

import java.io.IOException;

import com.dentalclinic.model.Appointment;
import com.dentalclinic.model.Bill;
import com.dentalclinic.model.Treatment;
import com.dentalclinic.service.AppointmentService;
import com.dentalclinic.service.BillService;
import com.dentalclinic.service.TreatmentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/bill")
public class BillServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private BillService billService;
    private AppointmentService appointmentService;
    private TreatmentService treatmentService;

    @Override
    public void init() throws ServletException {
        billService = new BillService();
        appointmentService = new AppointmentService();
        treatmentService = new TreatmentService();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String role = (String) request.getSession().getAttribute("role");
        if (role == null || !"receptionist".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/jsp/dashboard.jsp");
            return;
        }

        int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));
        double consultationFee = Double.parseDouble(request.getParameter("consultationFee"));
        double treatmentFee = Double.parseDouble(request.getParameter("treatmentFee"));

        double totalAmount = billService.calculateBill(consultationFee, treatmentFee);

        Bill bill = new Bill();
        bill.setAppointmentId(appointmentId);
        bill.setConsultationFee(consultationFee);
        bill.setTreatmentFee(treatmentFee);
        bill.setTotalAmount(totalAmount);

        boolean success = billService.saveBill(bill);

        if (success) {
            request.setAttribute("bill", bill);
            request.setAttribute("successMessage", "Bill generated successfully.");
        } else {
            request.setAttribute("errorMessage", "Failed to generate bill.");
        }

        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        request.setAttribute("appointment", appointment);

        request.getRequestDispatcher("/jsp/bill.jsp")
                .forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String role = (String) request.getSession().getAttribute("role");
        if (role == null || !"receptionist".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/jsp/dashboard.jsp");
            return;
        }

        String appointmentIdParam = request.getParameter("appointmentId");
        String print = request.getParameter("print");

        if (appointmentIdParam != null && !appointmentIdParam.isEmpty()) {

            int appointmentId = Integer.parseInt(appointmentIdParam);
            Appointment appointment = appointmentService.getAppointmentById(appointmentId);
            request.setAttribute("appointment", appointment);

            if ("true".equals(print)) {
                Bill bill = billService.getBillByAppointmentId(appointmentId);

                if (bill == null) {
                    request.setAttribute("errorMessage", "No bill found for this appointment.");
                    request.getRequestDispatcher("/jsp/bill.jsp").forward(request, response);
                    return;
                }

                request.setAttribute("bill", bill);
                request.getRequestDispatcher("/jsp/printBill.jsp").forward(request, response);
                return;
            }

            if (appointment != null) {
                Treatment treatment = treatmentService.getTreatmentById(appointment.getTreatmentId());
                if (treatment != null && treatment.getPrice() != null) {
                    request.setAttribute("autoTreatmentFee", treatment.getPrice());
                }
            }
        }

        request.getRequestDispatcher("/jsp/bill.jsp")
                .forward(request, response);
    }
}