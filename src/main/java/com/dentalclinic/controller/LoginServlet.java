package com.dentalclinic.controller;

import java.io.IOException;

import com.dentalclinic.service.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private LoginService loginService;

    @Override
    public void init() throws ServletException {
        loginService = new LoginService();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // Get login details from the form
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Validate login
        boolean isValidUser = loginService.login(username, password);

        if (isValidUser) {

         
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

         
            com.dentalclinic.model.User user = loginService.getUserByUsername(username);
            String role = (user != null && user.getRole() != null) ? user.getRole() : "receptionist";
            session.setAttribute("role", role);

         
            response.sendRedirect(request.getContextPath() + "/jsp/dashboard.jsp");

        } else {


            request.setAttribute("errorMessage", "Invalid Username or Password");

            request.getRequestDispatcher("/jsp/login.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
    }
}