package com.dentalclinic.service;

import java.util.List;
import com.dentalclinic.dao.AppointmentDAO;
import com.dentalclinic.model.Appointment;

public class AppointmentService {

    private AppointmentDAO appointmentDAO;

    public AppointmentService() {
        appointmentDAO = new AppointmentDAO();
    }

    public AppointmentService(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

    public int addAppointment(Appointment appointment) {

        if (!isValid(appointment)) {
            return -1;
        }

        return appointmentDAO.addAppointment(appointment);
    }

    public boolean updateAppointment(Appointment appointment) {

        if (appointment == null) {
            return false;
        }

        if (appointment.getAppointmentId() <= 0) {
            return false;
        }

        if (!isValid(appointment)) {
            return false;
        }

        return appointmentDAO.updateAppointment(appointment);
    }

    public boolean cancelAppointment(int appointmentId) {

        if (appointmentId <= 0) {
            return false;
        }

        return appointmentDAO.cancelAppointment(appointmentId);
    }

    public boolean deleteAppointment(int appointmentId) {

        if (appointmentId <= 0) {
            return false;
        }

        return appointmentDAO.deleteAppointment(appointmentId);
    }

    public Appointment getAppointmentById(int appointmentId) {
        return appointmentDAO.getAppointmentById(appointmentId);
    }

    public List<Appointment> searchAppointments(String keyword) {
        return appointmentDAO.searchAppointments(keyword);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentDAO.getAllAppointments();
    }

    public List<Appointment> getAppointmentsByDate(String date) {
        return appointmentDAO.getAppointmentsByDate(date);
    }

    /**
     * Shared validation for add and update - checks the fields common
     * to both operations before anything reaches the database. This
     * is the "proper validation mechanisms" your assignment brief asks
     * for under Task B.
     */
    private boolean isValid(Appointment appointment) {

        if (appointment == null) {
            return false;
        }

        if (appointment.getPatientId() <= 0) {
            return false;
        }

        if (appointment.getDentistId() <= 0) {
            return false;
        }

        if (appointment.getTreatmentId() <= 0) {
            return false;
        }

        if (appointment.getAppointmentDate() == null
                || appointment.getAppointmentDate().trim().isEmpty()) {
            return false;
        }

        if (appointment.getAppointmentTime() == null
                || appointment.getAppointmentTime().trim().isEmpty()) {
            return false;
        }

        return true;
    }
}