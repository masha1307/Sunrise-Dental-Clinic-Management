package com.dentalclinic.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.dentalclinic.model.Appointment;
import com.dentalclinic.util.DBConnection;

public class AppointmentDAO {

    public boolean addAppointment(Appointment appointment) {

        return false;
    }

    public boolean updateAppointment(Appointment appointment) {

        return false;
    }

    public boolean deleteAppointment(int appointmentId) {

        return false;
    }

    public Appointment getAppointmentById(int appointmentId) {

        return null;
    }

    public List<Appointment> searchAppointments(String keyword) {

        return new ArrayList<>();
    }

}