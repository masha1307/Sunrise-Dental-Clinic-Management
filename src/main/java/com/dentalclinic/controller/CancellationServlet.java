package com.dentalclinic.controller;

import java.io.IOException;
import java.util.List;

import com.dentalclinic.model.Appointment;
import com.dentalclinic.service.AppointmentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cancellations")
public class CancellationServlet extends HttpServlet {

    private AppointmentService appointmentService;

    @Override
    public void init() throws ServletException {
        appointmentService = new AppointmentService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String role = (String) request.getSession().getAttribute("role");
        if (role == null || !"receptionist".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/jsp/dashboard.jsp");
            return;
        }

        String date = request.getParameter("date");

        if (date != null && !date.isEmpty()) {
            List<Appointment> appointments = appointmentService.getAppointmentsByDate(date);
            request.setAttribute("appointments", appointments);
            request.setAttribute("selectedDate", date);
        }

        request.getRequestDispatcher("/jsp/cancellations.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String role = (String) request.getSession().getAttribute("role");
        if (role == null || !"receptionist".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/jsp/dashboard.jsp");
            return;
        }

        String action = request.getParameter("action");
        String date = request.getParameter("date");

        if ("cancel".equals(action)) {
            try {
                int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));
                boolean success = appointmentService.cancelAppointment(appointmentId);

                if (success) {
                    request.setAttribute("successMessage", "Appointment cancelled successfully.");
                } else {
                    request.setAttribute("errorMessage", "Failed to cancel appointment.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Invalid appointment ID.");
            }
        }

        if (date != null && !date.isEmpty()) {
            List<Appointment> appointments = appointmentService.getAppointmentsByDate(date);
            request.setAttribute("appointments", appointments);
            request.setAttribute("selectedDate", date);
        }

        request.getRequestDispatcher("/jsp/cancellations.jsp").forward(request, response);
    }
}