package com.dentalclinic.controller;

import java.io.IOException;

import com.dentalclinic.service.LoginService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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

            // Create session
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            // Redirect to dashboard
            response.sendRedirect("jsp/dashboard.jsp");

        } else {

            // Login failed
            request.setAttribute("errorMessage", "Invalid Username or Password");

            request.getRequestDispatcher("jsp/login.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect("jsp/login.jsp");
    }
}