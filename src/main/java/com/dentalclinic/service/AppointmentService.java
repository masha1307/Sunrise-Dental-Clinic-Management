package com.dentalclinic.service;

import java.util.List;
import com.dentalclinic.dao.AppointmentDAO;
import com.dentalclinic.model.Appointment;

public class AppointmentService {

    private AppointmentDAO appointmentDAO;

    public AppointmentService() {
        appointmentDAO = new AppointmentDAO();
    }

    public int addAppointment(Appointment appointment) {
        return appointmentDAO.addAppointment(appointment);
    }

    public boolean updateAppointment(Appointment appointment) {
        return appointmentDAO.updateAppointment(appointment);
    }

    public boolean cancelAppointment(int appointmentId) {
        return appointmentDAO.cancelAppointment(appointmentId);
    }

    public boolean deleteAppointment(int appointmentId) {
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
}