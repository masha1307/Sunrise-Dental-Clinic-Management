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

        String role = (String) request.getSession().getAttribute("role");
        if (role == null || !"receptionist".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/jsp/dashboard.jsp");
            return;
        }

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

        String role = (String) request.getSession().getAttribute("role");
        if (role == null || !"receptionist".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/jsp/dashboard.jsp");
            return;
        }

        if ("add".equals(action)) {

            try {

                Appointment appointment = new Appointment();

                int patientId = resolvePatientId(request, response);
                if (patientId == -1) {
                    doGet(request, response);
                    return;
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
                    response.sendRedirect(request.getContextPath() + "/bill?appointmentId=" + appointmentId);
                    return;
                } else {
                    request.setAttribute(
                            "errorMessage",
                            "Failed to add appointment."
                    );
                }

            } catch (Exception e) {

                e.printStackTrace();

                request.setAttribute(
                        "errorMessage",
                        "Invalid appointment details."
                );
            }

            doGet(request, response);

        } else if ("update".equals(action)) {

            try {

                String appointmentIdParam = request.getParameter("appointmentId");

                if (appointmentIdParam == null || appointmentIdParam.trim().isEmpty()) {
                    request.setAttribute("errorMessage",
                            "Appointment ID is required to update an appointment.");
                    doGet(request, response);
                    return;
                }

                int appointmentId = Integer.parseInt(appointmentIdParam.trim());

                Appointment appointment = new Appointment();
                appointment.setAppointmentId(appointmentId);

                int patientId = resolvePatientId(request, response);
                if (patientId == -1) {
                    doGet(request, response);
                    return;
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

                boolean success = appointmentService.updateAppointment(appointment);

                if (success) {
                    request.setAttribute("successMessage",
                            "Appointment #" + appointmentId + " updated successfully.");
                } else {
                    request.setAttribute("errorMessage",
                            "Failed to update appointment. Check that the Appointment ID exists.");
                }

            } catch (NumberFormatException e) {

                request.setAttribute("errorMessage",
                        "Appointment ID, Dentist, Treatment and Patient must be valid.");

            } catch (Exception e) {

                e.printStackTrace();

                request.setAttribute("errorMessage", "Invalid appointment details.");
            }

            doGet(request, response);
        }

        // NOTE: "delete" branch removed on purpose.
        // Cancelling an appointment is handled by /cancellations
        // (AppointmentDAO.cancelAppointment), not here.
    }


    private int resolvePatientId(HttpServletRequest request,
                                  HttpServletResponse response) {

        String patientIdParam = request.getParameter("patientId");

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
                return patient.getPatientId();
            } else {
                request.setAttribute("errorMessage", "Failed to add new patient.");
                return -1;
            }

        } else {
            return Integer.parseInt(patientIdParam);
        }
    }

}