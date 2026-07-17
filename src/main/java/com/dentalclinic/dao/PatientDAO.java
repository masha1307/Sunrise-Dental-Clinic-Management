package com.dentalclinic.dao;

import java.util.List;
import com.dentalclinic.model.Patient;

public interface PatientDAO {

    boolean addPatient(Patient patient);

    Patient getPatientById(int patientId);

    List<Patient> getAllPatients();

    boolean updatePatient(Patient patient);

    boolean deletePatient(int patientId);

}