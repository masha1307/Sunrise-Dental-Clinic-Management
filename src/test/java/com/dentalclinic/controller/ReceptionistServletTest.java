package com.dentalclinic.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceptionistServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    private ReceptionistServlet receptionistServlet;

    @BeforeEach
    void setUp() throws ServletException {
        receptionistServlet = new ReceptionistServlet();
        receptionistServlet.init();
    }

    @Test
    void testDoPost_Add() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("add");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        receptionistServlet.doPost(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoPost_Update() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("update");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        receptionistServlet.doPost(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoPost_Delete() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("delete");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        receptionistServlet.doPost(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoGet() throws ServletException, IOException {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        receptionistServlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
    }
}
