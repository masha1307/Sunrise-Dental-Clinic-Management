package com.dentalclinic.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogoutServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    private LogoutServlet logoutServlet;

    @BeforeEach
    void setUp() throws javax.servlet.ServletException {
        logoutServlet = new LogoutServlet();
        logoutServlet.init();
    }

    @Test
    void testDoPost() throws IOException, javax.servlet.ServletException {
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("/DentalClinicManagement");

        logoutServlet.doPost(request, response);

        verify(session).invalidate();
        verify(response).sendRedirect("/DentalClinicManagement/jsp/login.jsp");
    }

    @Test
    void testDoGet() throws IOException, javax.servlet.ServletException {
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("/DentalClinicManagement");

        logoutServlet.doGet(request, response);

        verify(session).invalidate();
        verify(response).sendRedirect("/DentalClinicManagement/jsp/login.jsp");
    }
}
