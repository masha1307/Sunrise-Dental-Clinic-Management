package com.dentalclinic.model;

import java.sql.Date;
import java.sql.Time;

public class Appointment {

    private int appointmentId;
    private String appointmentNumber;
    private int patientId;
    private int dentistId;
    private String treatmentType;
    private Date appointmentDate;
    private Time appointmentTime;

    public Appointment() {
    }

    public Appointment(int appointmentId, String appointmentNumber,
                       int patientId, int dentistId,
                       String treatmentType,
                       Date appointmentDate,
                       Time appointmentTime) {

        this.appointmentId = appointmentId;
        this.appointmentNumber = appointmentNumber;
        this.patientId = patientId;
        this.dentistId = dentistId;
        this.treatmentType = treatmentType;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
    }

    // Generate Getters and Setters using VS Code
}