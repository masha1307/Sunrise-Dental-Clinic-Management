package com.dentalclinic.service;


import java.util.List;

import com.dentalclinic.dao.DentistDAO;
import com.dentalclinic.model.Dentist;



public class DentistService {


    private DentistDAO dentistDAO;



    public DentistService(){

        dentistDAO = new DentistDAO();

    }



    public List<Dentist> getAllDentists(){

        return dentistDAO.getAllDentists();

    }

    public boolean addDentist(Dentist dentist) {
        return dentistDAO.addDentist(dentist);
    }


}