package com.dentalclinic.controller;

import com.dentalclinic.dao.AppointmentDAO;
import com.dentalclinic.model.Appointment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/appointmentSummary")
public class AppointmentSummaryServlet extends HttpServlet {

    private AppointmentDAO appointmentDAO;

    @Override
    public void init() throws ServletException {
        appointmentDAO = new AppointmentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String date = request.getParameter("date");
        if (date != null && !date.isEmpty()) {
            List<Appointment> appointments = appointmentDAO.getAppointmentsByDate(date);
            request.setAttribute("appointments", appointments);
            request.setAttribute("selectedDate", date);
        }

        request.getRequestDispatcher("/jsp/appointment_summary.jsp").forward(request, response);
    }

}