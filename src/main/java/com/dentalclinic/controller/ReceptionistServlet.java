package com.dentalclinic.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dentalclinic.model.Admin;
import com.dentalclinic.model.Receptionist;
import com.dentalclinic.service.ReceptionistService;

@WebServlet({"/receptionist", "/receptionistAdmin"})
public class ReceptionistServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ReceptionistService receptionistService;

    @Override
    public void init() throws ServletException {
        receptionistService = new ReceptionistService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        if (role == null || !"admin".equalsIgnoreCase(role)) {
            response.sendRedirect(request.getContextPath() + "/jsp/dashboard.jsp");
            return;
        }

        String action = request.getParameter("action");
        String accountType = request.getParameter("type"); // "receptionist" or "admin"

        if ("edit".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                if ("admin".equalsIgnoreCase(accountType)) {
                    Admin editAdmin = receptionistService.getAdminById(id);
                    if (editAdmin != null) {
                        request.setAttribute("editAdmin", editAdmin);
                        request.setAttribute("editAccountType", "admin");
                    } else {
                        request.setAttribute("errorMessage", "Admin account not found.");
                    }
                } else {
                    Receptionist editReceptionist = receptionistService.getReceptionistById(id);
                    if (editReceptionist != null) {
                        request.setAttribute("editReceptionist", editReceptionist);
                        request.setAttribute("editAccountType", "receptionist");
                    } else {
                        request.setAttribute("errorMessage", "Receptionist account not found.");
                    }
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Invalid account ID.");
            }
        }

        // Load both separate lists
        List<Receptionist> receptionists = receptionistService.getAllReceptionists();
        List<Admin> admins = receptionistService.getAllAdmins();

        request.setAttribute("receptionists", receptionists);
        request.setAttribute("admins", admins);

        request.getRequestDispatcher("/jsp/receptionist.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        if (role == null || !"admin".equalsIgnoreCase(role)) {
            response.sendRedirect(request.getContextPath() + "/jsp/dashboard.jsp");
            return;
        }

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
            String accountType = request.getParameter("accountType");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String contactNumber = request.getParameter("contactNumber");

            if (username == null || username.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Username cannot be empty.");
                return;
            }

            if (password == null || password.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Password cannot be empty.");
                return;
            }

            if ("admin".equalsIgnoreCase(accountType)) {
                if (receptionistService.isAdminUsernameTaken(username.trim(), 0)) {
                    request.setAttribute("errorMessage", "Username '" + username.trim() + "' is already taken.");
                    return;
                }
                Admin admin = new Admin();
                admin.setUsername(username.trim());
                admin.setPassword(password.trim());
                admin.setName(name);
                admin.setEmail(email);

                boolean result = receptionistService.addAdmin(admin);
                if (result) {
                    request.setAttribute("successMessage", "Admin account created successfully.");
                } else {
                    request.setAttribute("errorMessage", "Failed to create admin account.");
                }
            } else {
                if (receptionistService.isReceptionistUsernameTaken(username.trim(), 0)) {
                    request.setAttribute("errorMessage", "Username '" + username.trim() + "' is already taken.");
                    return;
                }
                Receptionist receptionist = new Receptionist();
                receptionist.setUsername(username.trim());
                receptionist.setPassword(password.trim());
                receptionist.setName(name);
                receptionist.setContactNumber(contactNumber);
                receptionist.setEmail(email);

                boolean result = receptionistService.addReceptionist(receptionist);
                if (result) {
                    request.setAttribute("successMessage", "Receptionist account created successfully.");
                } else {
                    request.setAttribute("errorMessage", "Failed to create receptionist account.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Invalid account details provided.");
        }
    }

    private void handleUpdate(HttpServletRequest request) {
        try {
            String accountType = request.getParameter("accountType");
            int id = Integer.parseInt(request.getParameter("id"));
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String contactNumber = request.getParameter("contactNumber");

            if (username == null || username.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Username cannot be empty.");
                return;
            }

            if ("admin".equalsIgnoreCase(accountType)) {
                if (receptionistService.isAdminUsernameTaken(username.trim(), id)) {
                    request.setAttribute("errorMessage", "Username '" + username.trim() + "' is already in use.");
                    return;
                }
                Admin admin = new Admin();
                admin.setAdminId(id);
                admin.setUsername(username.trim());
                if (password != null && !password.trim().isEmpty()) {
                    admin.setPassword(password.trim());
                }
                admin.setName(name);
                admin.setEmail(email);

                boolean result = receptionistService.updateAdmin(admin);
                if (result) {
                    request.setAttribute("successMessage", "Admin account updated successfully.");
                } else {
                    request.setAttribute("errorMessage", "Failed to update admin account.");
                }
            } else {
                if (receptionistService.isReceptionistUsernameTaken(username.trim(), id)) {
                    request.setAttribute("errorMessage", "Username '" + username.trim() + "' is already in use.");
                    return;
                }
                Receptionist receptionist = new Receptionist();
                receptionist.setReceptionistId(id);
                receptionist.setUsername(username.trim());
                if (password != null && !password.trim().isEmpty()) {
                    receptionist.setPassword(password.trim());
                }
                receptionist.setName(name);
                receptionist.setContactNumber(contactNumber);
                receptionist.setEmail(email);

                boolean result = receptionistService.updateReceptionist(receptionist);
                if (result) {
                    request.setAttribute("successMessage", "Receptionist account updated successfully.");
                } else {
                    request.setAttribute("errorMessage", "Failed to update receptionist account.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Invalid update details.");
        }
    }

    private void handleDelete(HttpServletRequest request) {
        try {
            String accountType = request.getParameter("accountType");
            int id = Integer.parseInt(request.getParameter("id"));

            HttpSession session = request.getSession(false);
            String currentUsername = (session != null) ? (String) session.getAttribute("username") : null;

            if ("admin".equalsIgnoreCase(accountType)) {
                Admin targetAdmin = receptionistService.getAdminById(id);
                if (targetAdmin != null && currentUsername != null && currentUsername.equalsIgnoreCase(targetAdmin.getUsername())) {
                    request.setAttribute("errorMessage", "You cannot delete your own active administrator account.");
                    return;
                }
                boolean result = receptionistService.deleteAdmin(id);
                if (result) {
                    request.setAttribute("successMessage", "Admin account deleted successfully.");
                } else {
                    request.setAttribute("errorMessage", "Failed to delete admin account.");
                }
            } else {
                boolean result = receptionistService.deleteReceptionist(id);
                if (result) {
                    request.setAttribute("successMessage", "Receptionist account deleted successfully.");
                } else {
                    request.setAttribute("errorMessage", "Failed to delete receptionist account.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Invalid account ID for deletion.");
        }
    }
}
