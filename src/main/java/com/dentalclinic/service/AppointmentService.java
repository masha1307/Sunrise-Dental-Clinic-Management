package com.dentalclinic.service;

import java.util.List;

import com.dentalclinic.dao.AppointmentDAO;
import com.dentalclinic.model.Appointment;

public class AppointmentService {


    private AppointmentDAO appointmentDAO;


    public AppointmentService() {

        appointmentDAO = new AppointmentDAO();

    }


    public boolean addAppointment(Appointment appointment) {


        if (appointment == null) {
            return false;
        }


        if (appointment.getPatientId() <= 0) {
            return false;
        }


        if (appointment.getDentistId() <= 0) {
            return false;
        }


        if (appointment.getAppointmentDate() == null ||
                appointment.getAppointmentDate().isEmpty()) {

            return false;
        }


        return appointmentDAO.addAppointment(appointment);

    }



    public boolean updateAppointment(Appointment appointment) {


        if (appointment == null) {
            return false;
        }


        return appointmentDAO.updateAppointment(appointment);

    }



    public boolean deleteAppointment(int appointmentId) {


        if (appointmentId <= 0) {
            return false;
        }


        return appointmentDAO.deleteAppointment(appointmentId);

    }



    public List<Appointment> searchAppointments(String keyword) {


        if (keyword == null) {

            keyword = "";

        }


        return appointmentDAO.searchAppointments(keyword);

    }

}