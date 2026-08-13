package com.dentalclinic.service;

import java.util.List;

import com.dentalclinic.dao.AppointmentDAO;
import com.dentalclinic.model.Appointment;


public class AppointmentService {


    private AppointmentDAO appointmentDAO;



    public AppointmentService() {

        appointmentDAO = new AppointmentDAO();

    }



    // Add Appointment
    public int addAppointment(Appointment appointment) {

        return appointmentDAO.addAppointment(appointment);

    }





    // Update Appointment
    public boolean updateAppointment(Appointment appointment) {

        return appointmentDAO.updateAppointment(appointment);

    }





    // Delete Appointment
    public boolean deleteAppointment(int appointmentId) {

        return appointmentDAO.deleteAppointment(appointmentId);

    }





    // Get Appointment By ID
    public Appointment getAppointmentById(int appointmentId) {

        return appointmentDAO.getAppointmentById(appointmentId);

    }





    // Search Appointment
    public List<Appointment> searchAppointments(String keyword) {

        return appointmentDAO.searchAppointments(keyword);

    }

    // Get All Appointments
    public List<Appointment> getAllAppointments() {

        return appointmentDAO.getAllAppointments();

    }


}