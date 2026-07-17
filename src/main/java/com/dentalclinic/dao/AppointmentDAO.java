package com.dentalclinic.dao;

import java.util.List;
import com.dentalclinic.model.Appointment;

public interface AppointmentDAO {

    boolean addAppointment(Appointment appointment);

    Appointment getAppointmentByNumber(String appointmentNumber);

    List<Appointment> getAllAppointments();

    boolean updateAppointment(Appointment appointment);

    boolean deleteAppointment(int appointmentId);

}