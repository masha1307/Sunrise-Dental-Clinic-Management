package com.dentalclinic.controller;

import java.io.IOException;
import java.util.List;

import com.dentalclinic.model.Dentist;
import com.dentalclinic.service.DentistService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dentist")
public class DentistServlet extends HttpServlet {

    private DentistService dentistService;

    @Override
    public void init() throws ServletException {
        dentistService = new DentistService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("dentistId"));
                Dentist editDentist = dentistService.getDentistById(id);

                if (editDentist != null) {
                    request.setAttribute("editDentist", editDentist);
                } else {
                    request.setAttribute("errorMessage", "Dentist not found.");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Invalid dentist ID.");
            }
        }

        // load dentists to show in management page
        List<Dentist> dentists = dentistService.getAllDentists();
        request.setAttribute("dentists", dentists);

        request.getRequestDispatcher("/jsp/dentist.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            handleAdd(request);
        } else if ("update".equals(action)) {
            handleUpdate(request);
        } else if ("delete".equals(action)) {
            handleDelete(request);
        }

        doGet(request, response);
    }

    private void handleAdd(HttpServletRequest request) {
        try {
            Dentist dentist = new Dentist();
            dentist.setDentistName(request.getParameter("dentistName"));
            dentist.setSpecialization(request.getParameter("specialization"));
            dentist.setContactNumber(request.getParameter("contactNumber"));
            dentist.setEmail(request.getParameter("email"));

            boolean result = dentistService.addDentist(dentist);

            if (result) {
                request.setAttribute("successMessage", "Dentist added successfully.");
            } else {
                request.setAttribute("errorMessage", "Failed to add dentist.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Invalid dentist details.");
        }
    }

    private void handleUpdate(HttpServletRequest request) {
        try {
            Dentist dentist = new Dentist();
            dentist.setDentistId(Integer.parseInt(request.getParameter("dentistId")));
            dentist.setDentistName(request.getParameter("dentistName"));
            dentist.setSpecialization(request.getParameter("specialization"));
            dentist.setContactNumber(request.getParameter("contactNumber"));
            dentist.setEmail(request.getParameter("email"));

            boolean result = dentistService.updateDentist(dentist);

            if (result) {
                request.setAttribute("successMessage", "Dentist updated successfully.");
            } else {
                request.setAttribute("errorMessage", "Failed to update dentist.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Invalid dentist details.");
        }
    }

    private void handleDelete(HttpServletRequest request) {
        try {
            int id = Integer.parseInt(request.getParameter("dentistId"));
            boolean result = dentistService.deleteDentist(id);

            if (result) {
                request.setAttribute("successMessage", "Dentist deleted successfully.");
            } else {
                request.setAttribute("errorMessage", "Failed to delete dentist.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Invalid dentist ID.");
        }
    }

}