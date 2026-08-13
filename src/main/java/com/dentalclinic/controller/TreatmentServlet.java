package com.dentalclinic.controller;

import java.io.IOException;

import com.dentalclinic.model.Treatment;
import com.dentalclinic.service.TreatmentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/treatment")
public class TreatmentServlet extends HttpServlet {


    private TreatmentService treatmentService;


    @Override
    public void init() throws ServletException {

        treatmentService = new TreatmentService();

    }


    @Override
    protected void doGet(HttpServletRequest request,
                        HttpServletResponse response)
                        throws ServletException, IOException {

        request.getRequestDispatcher(
                "/jsp/treatment.jsp"
        ).forward(request, response);

    }


    @Override
    protected void doPost(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException {

        String action = request.getParameter("action");


        if ("add".equals(action)) {


            try {

                Treatment treatment = new Treatment();

                treatment.setTreatmentName(
                        request.getParameter("treatmentName")
                );

                boolean result =
                        treatmentService.addTreatment(treatment);



                if(result){

                    request.setAttribute(
                            "successMessage",
                            "Treatment added successfully."
                    );

                }
                else{

                    request.setAttribute(
                            "errorMessage",
                            "Failed to add treatment."
                    );

                }



            } catch(Exception e){

                e.printStackTrace();

                request.setAttribute(
                        "errorMessage",
                        "Invalid treatment details."
                );

            }

            doGet(request,response);

        }

    }

}
