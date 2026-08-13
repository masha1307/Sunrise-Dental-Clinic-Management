package com.dentalclinic.model;

public class Appointment {

    private int appointmentId;

    private int patientId;

    private int dentistId;

    private int treatmentId;

    private String appointmentDate;

    private String appointmentTime;

    private String patientName;

    private String dentistName;

    private String treatmentName;


    // Default Constructor
    public Appointment() {

    }



    // Parameterized Constructor
    public Appointment(int appointmentId,
                       int patientId,
                       int dentistId,
                       int treatmentId,
                       String appointmentDate,
                       String appointmentTime) {


        this.appointmentId = appointmentId;

        this.patientId = patientId;

        this.dentistId = dentistId;

        this.treatmentId = treatmentId;

        this.appointmentDate = appointmentDate;

        this.appointmentTime = appointmentTime;

    }




    // Appointment ID

    public int getAppointmentId() {

        return appointmentId;

    }


    public void setAppointmentId(int appointmentId) {

        this.appointmentId = appointmentId;

    }




    // Patient ID

    public int getPatientId() {

        return patientId;

    }


    public void setPatientId(int patientId) {

        this.patientId = patientId;

    }





    // Dentist ID

    public int getDentistId() {

        return dentistId;

    }


    public void setDentistId(int dentistId) {

        this.dentistId = dentistId;

    }





    // Treatment ID

    public int getTreatmentId() {

        return treatmentId;

    }


    public void setTreatmentId(int treatmentId) {

        this.treatmentId = treatmentId;

    }





    // Appointment Date

    public String getAppointmentDate() {

        return appointmentDate;

    }


    public void setAppointmentDate(String appointmentDate) {

        this.appointmentDate = appointmentDate;

    }





    // Appointment Time

    public String getAppointmentTime() {

        return appointmentTime;

    }


    public void setAppointmentTime(String appointmentTime) {

        this.appointmentTime = appointmentTime;

    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }


}