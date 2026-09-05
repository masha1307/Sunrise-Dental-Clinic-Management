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
class BillServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    private BillServlet billServlet;

    @BeforeEach
    void setUp() throws ServletException {
        billServlet = new BillServlet();
        billServlet.init();
    }

    @Test
    void testDoPost_Save() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("save");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        billServlet.doPost(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoGet() throws ServletException, IOException {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        billServlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
    }
}
