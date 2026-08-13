package com.dentalclinic.controller;

import java.io.IOException;

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
    protected void doGet(HttpServletRequest request,
                        HttpServletResponse response)
                        throws ServletException, IOException {

        request.getRequestDispatcher(
                "/jsp/dentist.jsp"
        ).forward(request, response);

    }


    @Override
    protected void doPost(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException {

        String action = request.getParameter("action");


        if ("add".equals(action)) {


            try {

                Dentist dentist = new Dentist();

                dentist.setDentistName(
                        request.getParameter("dentistName")
                );

                dentist.setSpecialization(
                        request.getParameter("specialization")
                );

                dentist.setContactNumber(
                        request.getParameter("contactNumber")
                );

                dentist.setEmail(
                        request.getParameter("email")
                );

                boolean result =
                        dentistService.addDentist(dentist);



                if(result){

                    request.setAttribute(
                            "successMessage",
                            "Dentist added successfully."
                    );

                }
                else{

                    request.setAttribute(
                            "errorMessage",
                            "Failed to add dentist."
                    );

                }



            } catch(Exception e){

                e.printStackTrace();

                request.setAttribute(
                        "errorMessage",
                        "Invalid dentist details."
                );

            }

            doGet(request,response);

        }

    }

}
