package com.dentalclinic.controller;

import java.io.IOException;

import com.dentalclinic.model.Appointment;
import com.dentalclinic.model.Patient;
import com.dentalclinic.service.AppointmentService;
import com.dentalclinic.service.DentistService;
import com.dentalclinic.service.TreatmentService;
import com.dentalclinic.service.PatientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/appointment")
public class AppointmentServlet extends HttpServlet {


    private AppointmentService appointmentService;
    private DentistService dentistService;
    private TreatmentService treatmentService;
    private PatientService patientService;


    @Override
    public void init() throws ServletException {

        appointmentService = new AppointmentService();
        dentistService = new DentistService();
        treatmentService = new TreatmentService();
        patientService = new PatientService();

    }



    @Override
    protected void doGet(HttpServletRequest request,
                        HttpServletResponse response)
                        throws ServletException, IOException {


        request.setAttribute(
                "dentists",
                dentistService.getAllDentists()
        );


        request.setAttribute(
                "treatments",
                treatmentService.getAllTreatments()
        );

        request.setAttribute(
                "patients",
                patientService.getAllPatients()
        );


        request.getRequestDispatcher(
                "/jsp/appointment.jsp"
        ).forward(request, response);

    }



    @Override
    protected void doPost(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException {


        String action = request.getParameter("action");


        if ("add".equals(action)) {


            try {


                Appointment appointment = new Appointment();

                String patientIdParam = request.getParameter("patientId");
                int patientId;

                if ("new".equals(patientIdParam)) {
                    Patient patient = new Patient();
                    patient.setPatientName(request.getParameter("newPatientName"));
                    patient.setAge(Integer.parseInt(request.getParameter("newPatientAge")));
                    patient.setGender(request.getParameter("newPatientGender"));
                    patient.setContactNumber(request.getParameter("newPatientContact"));
                    patient.setEmail(request.getParameter("newPatientEmail"));
                    patient.setAddress(request.getParameter("newPatientAddress"));

                    boolean patientAdded = patientService.addPatient(patient);
                    if (patientAdded) {
                        patientId = patient.getPatientId();
                    } else {
                        request.setAttribute("errorMessage", "Failed to add new patient.");
                        doGet(request, response);
                        return;
                    }
                } else {
                    patientId = Integer.parseInt(patientIdParam);
                }

                appointment.setPatientId(patientId);


                appointment.setDentistId(
                        Integer.parseInt(
                                request.getParameter("dentistId")
                        )
                );


                appointment.setTreatmentId(
                        Integer.parseInt(
                                request.getParameter("treatmentId")
                        )
                );


                appointment.setAppointmentDate(
                        request.getParameter("appointmentDate")
                );


                appointment.setAppointmentTime(
                        request.getParameter("appointmentTime")
                );



                int appointmentId = appointmentService.addAppointment(appointment);

                if (appointmentId > 0) {
                    // Redirect to bill page with the newly created appointment ID
                    response.sendRedirect(request.getContextPath() + "/bill?appointmentId=" + appointmentId);
                    return;
                } else {
                    request.setAttribute(
                            "errorMessage",
                            "Failed to add appointment."
                    );
                }



            } catch(Exception e){


                e.printStackTrace();


                request.setAttribute(
                        "errorMessage",
                        "Invalid appointment details."
                );

            }



            doGet(request,response);


        }

    }

}