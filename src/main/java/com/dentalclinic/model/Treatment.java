package com.dentalclinic.model;

public class Treatment {


    private int treatmentId;

    private String treatmentName;
    
    private Double price;
    
    private String durationMinutes;




    public int getTreatmentId() {

        return treatmentId;

    }



    public void setTreatmentId(int treatmentId) {

        this.treatmentId = treatmentId;

    }



    public String getTreatmentName() {

        return treatmentName;

    }



    public void setTreatmentName(String treatmentName) {

        this.treatmentName = treatmentName;

    }
    
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    
    
    public String getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(String durationMinutes) {
        this.durationMinutes = durationMinutes;
    }




}