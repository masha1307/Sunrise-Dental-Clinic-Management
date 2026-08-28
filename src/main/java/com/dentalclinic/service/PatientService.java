package com.dentalclinic.service;

import java.util.List;

import com.dentalclinic.dao.PatientDAO;
import com.dentalclinic.model.Patient;

public class PatientService {

    private PatientDAO patientDAO;

    public PatientService() {
        patientDAO = new PatientDAO();
    }

    public List<Patient> getAllPatients() {
        return patientDAO.getAllPatients();
    }

    public boolean addPatient(Patient patient) {
        return patientDAO.addPatient(patient);
    }

    public Patient getPatientById(int patientId) {
        return patientDAO.getPatientById(patientId);
    }

    public boolean updatePatient(Patient patient) {
        return patientDAO.updatePatient(patient);
    }

    public boolean deletePatient(int patientId) {
        return patientDAO.deletePatient(patientId);
    }
}

