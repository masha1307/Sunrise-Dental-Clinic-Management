package com.dentalclinic.controller;

import java.io.IOException;
import java.util.List;

import com.dentalclinic.model.Treatment;
import com.dentalclinic.service.TreatmentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/treatmentAdmin")
public class TreatmentServlet extends HttpServlet {

    private TreatmentService treatmentService;

    @Override
    public void init() throws ServletException {
        treatmentService = new TreatmentService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("treatmentId"));
                Treatment editTreatment = treatmentService.getTreatmentById(id);

                if (editTreatment != null) {
                    request.setAttribute("editTreatment", editTreatment);
                } else {
                    request.setAttribute("errorMessage", "Treatment not found.");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Invalid treatment ID.");
            }
        }

        List<Treatment> treatments = treatmentService.getAllTreatments();
        request.setAttribute("treatments", treatments);

        request.getRequestDispatcher("/jsp/treatment.jsp").forward(request, response);
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
            Treatment treatment = new Treatment();
            treatment.setTreatmentName(request.getParameter("treatmentName"));
            treatment.setPrice(parsePrice(request.getParameter("price")));
            treatment.setDurationMinutes(request.getParameter("durationMinutes"));

            boolean result = treatmentService.addTreatment(treatment);

            if (result) {
                request.setAttribute("successMessage", "Treatment added successfully.");
            } else {
                request.setAttribute("errorMessage", "Failed to add treatment.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Invalid treatment details.");
        }
    }

    private void handleUpdate(HttpServletRequest request) {
        try {
            Treatment treatment = new Treatment();
            treatment.setTreatmentId(Integer.parseInt(request.getParameter("treatmentId")));
            treatment.setTreatmentName(request.getParameter("treatmentName"));
            treatment.setPrice(parsePrice(request.getParameter("price")));
            treatment.setDurationMinutes(request.getParameter("durationMinutes"));

            boolean result = treatmentService.updateTreatment(treatment);

            if (result) {
                request.setAttribute("successMessage", "Treatment updated successfully.");
            } else {
                request.setAttribute("errorMessage", "Failed to update treatment.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Invalid treatment details.");
        }
    }

    private void handleDelete(HttpServletRequest request) {
        try {
            int id = Integer.parseInt(request.getParameter("treatmentId"));
            boolean result = treatmentService.deleteTreatment(id);

            if (result) {
                request.setAttribute("successMessage", "Treatment deleted successfully.");
            } else {
                request.setAttribute("errorMessage", "Failed to delete treatment.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Invalid treatment ID.");
        }
    }

    private Double parsePrice(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}