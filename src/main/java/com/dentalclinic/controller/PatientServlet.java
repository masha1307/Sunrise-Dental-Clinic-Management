package com.dentalclinic.controller;

import java.io.IOException;

import com.dentalclinic.model.Patient;
import com.dentalclinic.service.PatientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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

        request.getRequestDispatcher(
                "/jsp/patient.jsp"
        ).forward(request, response);

    }


    @Override
    protected void doPost(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException {

        String action = request.getParameter("action");


        if ("add".equals(action)) {


            try {

                Patient patient = new Patient();

                patient.setPatientName(
                        request.getParameter("patientName")
                );

                patient.setAge(
                        Integer.parseInt(
                                request.getParameter("age")
                        )
                );

                patient.setGender(
                        request.getParameter("gender")
                );

                patient.setContactNumber(
                        request.getParameter("contactNumber")
                );

                patient.setEmail(
                        request.getParameter("email")
                );

                patient.setAddress(
                        request.getParameter("address")
                );

                boolean result =
                        patientService.addPatient(patient);



                if(result){

                    request.setAttribute(
                            "successMessage",
                            "Patient added successfully."
                    );

                }
                else{

                    request.setAttribute(
                            "errorMessage",
                            "Failed to add patient."
                    );

                }



            } catch(Exception e){

                e.printStackTrace();

                request.setAttribute(
                        "errorMessage",
                        "Invalid patient details."
                );

            }

            doGet(request,response);

        }

    }

}
