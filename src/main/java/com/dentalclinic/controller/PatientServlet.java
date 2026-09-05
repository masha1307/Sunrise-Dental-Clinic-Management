package com.dentalclinic.controller;

import java.io.IOException;
import java.util.List;

import com.dentalclinic.model.Patient;
import com.dentalclinic.service.PatientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/patient")
public class PatientServlet extends HttpServlet {

    private PatientService patientService;

    @Override
    public void init() throws ServletException {
        patientService = new PatientService();
    }

        @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException {

        // Auth guard
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        // Flash messages from PRG redirect
        String successMessage = (String) session.getAttribute("successMessage");
        String errorMessage   = (String) session.getAttribute("errorMessage");
        session.removeAttribute("successMessage");
        session.removeAttribute("errorMessage");
        if (successMessage != null) request.setAttribute("successMessage", successMessage);
        if (errorMessage   != null) request.setAttribute("errorMessage",   errorMessage);

        // Handle edit mode: ?action=edit&id=X
        String action = request.getParameter("action");
        if ("edit".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Patient editPatient = patientService.getPatientById(id);
                if (editPatient != null) {
                    request.setAttribute("editPatient", editPatient);
                }
            } catch (NumberFormatException e) {
                // ignore bad id, fall through to normal view
            }
        }

        // Always load patient list
        List<Patient> patients = patientService.getAllPatients();
        request.setAttribute("patients", patients);

        request.getRequestDispatcher("/jsp/patient.jsp").forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws ServletException, IOException {

        // Auth guard
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            handleAdd(request, session);

        } else if ("update".equals(action)) {
            handleUpdate(request, session);

        } else if ("delete".equals(action)) {
            handleDelete(request, session);
        }

        // PRG – always redirect after POST
        response.sendRedirect(request.getContextPath() + "/patient");
    }


    private void handleAdd(HttpServletRequest request, HttpSession session) {
        try {
            Patient patient = buildPatientFromRequest(request);
            boolean result = patientService.addPatient(patient);
            if (result) {
                session.setAttribute("successMessage",
                        "Patient \"" + patient.getPatientName() + "\" added successfully.");
            } else {
                session.setAttribute("errorMessage", "Failed to add patient. Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Invalid patient details: " + e.getMessage());
        }
    }

    private void handleUpdate(HttpServletRequest request, HttpSession session) {
        try {
            int id = Integer.parseInt(request.getParameter("patientId"));
            Patient patient = buildPatientFromRequest(request);
            patient.setPatientId(id);
            boolean result = patientService.updatePatient(patient);
            if (result) {
                session.setAttribute("successMessage",
                        "Patient #" + id + " updated successfully.");
            } else {
                session.setAttribute("errorMessage", "Failed to update patient. Patient may not exist.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Invalid update details: " + e.getMessage());
        }
    }

    private void handleDelete(HttpServletRequest request, HttpSession session) {
        try {
            int id = Integer.parseInt(request.getParameter("patientId"));
            boolean result = patientService.deletePatient(id);
            if (result) {
                session.setAttribute("successMessage", "Patient #" + id + " deleted successfully.");
            } else {
                session.setAttribute("errorMessage", "Failed to delete patient. Patient may not exist.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Invalid delete request: " + e.getMessage());
        }
    }

    /** Build a Patient object from form parameters (no patientId set). */
    private Patient buildPatientFromRequest(HttpServletRequest request) {
        Patient p = new Patient();
        p.setPatientName(request.getParameter("patientName"));
        p.setAge(Integer.parseInt(request.getParameter("age")));
        p.setGender(request.getParameter("gender"));
        p.setContactNumber(request.getParameter("contactNumber"));
        p.setEmail(request.getParameter("email"));
        p.setAddress(request.getParameter("address"));
        return p;
    }
}
