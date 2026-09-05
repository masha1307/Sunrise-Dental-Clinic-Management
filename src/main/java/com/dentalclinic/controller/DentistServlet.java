package com.dentalclinic.controller;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

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

    // ---- Validation patterns ----
    // Name: letters, spaces, dots, apostrophes only (e.g. "Dr. John O'Neil") - 2 to 50 chars
    private static final Pattern NAME_PATTERN =
            Pattern.compile("^[a-zA-Z.'\\s]{2,50}$");

    // Specialization: letters, spaces, commas only - 2 to 50 chars
    private static final Pattern SPECIALIZATION_PATTERN =
            Pattern.compile("^[a-zA-Z,\\s]{2,50}$");

    // Contact number: digits, spaces, +, -, ( ) only - 7 to 15 characters
    private static final Pattern CONTACT_PATTERN =
            Pattern.compile("^[0-9+()\\-\\s]{7,15}$");

    // Basic email format check
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");

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

    /**
     * Validates the dentist form fields.
     * Returns null if all fields are valid, otherwise returns an error message.
     */
    private String validateDentistFields(String name, String specialization,
                                          String contactNumber, String email) {

        if (name == null || name.trim().isEmpty()) {
            return "Dentist name is required.";
        }
        if (!NAME_PATTERN.matcher(name.trim()).matches()) {
            return "Dentist name must contain letters only (2-50 characters).";
        }

        if (specialization == null || specialization.trim().isEmpty()) {
            return "Specialization is required.";
        }
        if (!SPECIALIZATION_PATTERN.matcher(specialization.trim()).matches()) {
            return "Specialization must contain letters only (2-50 characters).";
        }

        if (contactNumber == null || contactNumber.trim().isEmpty()) {
            return "Contact number is required.";
        }
        if (!CONTACT_PATTERN.matcher(contactNumber.trim()).matches()) {
            return "Contact number must contain digits only (7-15 characters, e.g. 555-0101).";
        }

        if (email == null || email.trim().isEmpty()) {
            return "Email address is required.";
        }
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            return "Please enter a valid email address (e.g. doctor@clinic.com).";
        }

        return null; // all good
    }

    private void handleAdd(HttpServletRequest request) {
        String name = request.getParameter("dentistName");
        String specialization = request.getParameter("specialization");
        String contactNumber = request.getParameter("contactNumber");
        String email = request.getParameter("email");

        String validationError = validateDentistFields(name, specialization, contactNumber, email);

        if (validationError != null) {
            request.setAttribute("errorMessage", validationError);

            // Re-populate the form with what the user typed so they don't lose their input
            Dentist typedDentist = new Dentist();
            typedDentist.setDentistName(name);
            typedDentist.setSpecialization(specialization);
            typedDentist.setContactNumber(contactNumber);
            typedDentist.setEmail(email);
            request.setAttribute("formDentist", typedDentist);
            return; // stop here - do NOT save to database
        }

        try {
            Dentist dentist = new Dentist();
            dentist.setDentistName(name.trim());
            dentist.setSpecialization(specialization.trim());
            dentist.setContactNumber(contactNumber.trim());
            dentist.setEmail(email.trim());

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
        String name = request.getParameter("dentistName");
        String specialization = request.getParameter("specialization");
        String contactNumber = request.getParameter("contactNumber");
        String email = request.getParameter("email");

        String validationError = validateDentistFields(name, specialization, contactNumber, email);

        if (validationError != null) {
            request.setAttribute("errorMessage", validationError);

            // Re-populate the edit form with what the user typed
            try {
                Dentist typedDentist = new Dentist();
                typedDentist.setDentistId(Integer.parseInt(request.getParameter("dentistId")));
                typedDentist.setDentistName(name);
                typedDentist.setSpecialization(specialization);
                typedDentist.setContactNumber(contactNumber);
                typedDentist.setEmail(email);
                request.setAttribute("editDentist", typedDentist);
            } catch (NumberFormatException e) {
                // ignore, error message already set
            }
            return; // stop here - do NOT update database
        }

        try {
            Dentist dentist = new Dentist();
            dentist.setDentistId(Integer.parseInt(request.getParameter("dentistId")));
            dentist.setDentistName(name.trim());
            dentist.setSpecialization(specialization.trim());
            dentist.setContactNumber(contactNumber.trim());
            dentist.setEmail(email.trim());

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