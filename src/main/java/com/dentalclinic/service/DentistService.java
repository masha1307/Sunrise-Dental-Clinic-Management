package com.dentalclinic.service;

import java.util.List;
import com.dentalclinic.dao.DentistDAO;
import com.dentalclinic.model.Dentist;

public class DentistService {

    private DentistDAO dentistDAO;

    public DentistService(){
        dentistDAO = new DentistDAO();
    }

    public DentistService(DentistDAO dentistDAO){
        this.dentistDAO = dentistDAO;
    }

    public List<Dentist> getAllDentists(){
        return dentistDAO.getAllDentists();
    }

    public Dentist getDentistById(int id) {
        return dentistDAO.getDentistById(id);
    }

    public boolean addDentist(Dentist dentist) {
        return dentistDAO.addDentist(dentist);
    }

    public boolean updateDentist(Dentist dentist) {
        return dentistDAO.updateDentist(dentist);
    }

    public boolean deleteDentist(int id) {
        return dentistDAO.deleteDentist(id);
    }
 
    public boolean isDuplicateEmail(String email) {
        return dentistDAO.getAllDentists().stream()
                .anyMatch(d -> d.getEmail().equalsIgnoreCase(email));
    }

}