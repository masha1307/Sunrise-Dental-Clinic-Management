package com.dentalclinic.service;


import java.util.List;

import com.dentalclinic.dao.TreatmentDAO;
import com.dentalclinic.model.Treatment;



public class TreatmentService {


    private TreatmentDAO treatmentDAO;



    public TreatmentService(){

        treatmentDAO = new TreatmentDAO();

    }



    public List<Treatment> getAllTreatments(){

        return treatmentDAO.getAllTreatments();

    }

    public boolean addTreatment(Treatment treatment) {
        return treatmentDAO.addTreatment(treatment);
    }


}