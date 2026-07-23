package com.dentalclinic.controller;

import java.io.IOException;

import com.dentalclinic.model.Appointment;
import com.dentalclinic.service.AppointmentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/appointment")
public class AppointmentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private AppointmentService appointmentService;

    @Override
    public void init() throws ServletException {
        appointmentService = new AppointmentService();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("add".equals(action)) {

            Appointment appointment = new Appointment();

            appointment.setPatientId(Integer.parseInt(request.getParameter("patientId")));
            appointment.setDentistId(Integer.parseInt(request.getParameter("dentistId")));
            appointment.setTreatment(request.getParameter("treatment"));
            appointment.setAppointmentDate(request.getParameter("appointmentDate"));
            appointment.setAppointmentTime(request.getParameter("appointmentTime"));

            boolean success = appointmentService.addAppointment(appointment);

            if (success) {
                request.setAttribute("successMessage", "Appointment added successfully.");
            } else {
                request.setAttribute("errorMessage", "Failed to add appointment.");
            }

            request.getRequestDispatcher("jsp/appointment.jsp")
                    .forward(request, response);

        } else if ("update".equals(action)) {

            Appointment appointment = new Appointment();

            appointment.setAppointmentId(
                    Integer.parseInt(request.getParameter("appointmentId")));

            appointment.setPatientId(
                    Integer.parseInt(request.getParameter("patientId")));

            appointment.setDentistId(
                    Integer.parseInt(request.getParameter("dentistId")));

            appointment.setTreatment(
                    request.getParameter("treatment"));

            appointment.setAppointmentDate(
                    request.getParameter("appointmentDate"));

            appointment.setAppointmentTime(
                    request.getParameter("appointmentTime"));

            boolean success = appointmentService.updateAppointment(appointment);

            if (success) {
                request.setAttribute("successMessage", "Appointment updated successfully.");
            } else {
                request.setAttribute("errorMessage", "Failed to update appointment.");
            }

            request.getRequestDispatcher("jsp/appointment.jsp")
                    .forward(request, response);

        } else if ("delete".equals(action)) {

            int appointmentId =
                    Integer.parseInt(request.getParameter("appointmentId"));

            boolean success =
                    appointmentService.deleteAppointment(appointmentId);

            if (success) {
                request.setAttribute("successMessage", "Appointment deleted successfully.");
            } else {
                request.setAttribute("errorMessage", "Failed to delete appointment.");
            }

            request.getRequestDispatcher("jsp/appointment.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect("jsp/appointment.jsp");
    }
}