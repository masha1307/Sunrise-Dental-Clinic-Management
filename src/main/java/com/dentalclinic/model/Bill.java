package com.dentalclinic.model;

public class Bill {

    private int billId;
    private int appointmentId;
    private double consultationFee;
    private double treatmentFee;
    private double totalAmount;

    public Bill() {
    }

    public Bill(int billId, int appointmentId,
                double consultationFee,
                double treatmentFee,
                double totalAmount) {

        this.billId = billId;
        this.appointmentId = appointmentId;
        this.consultationFee = consultationFee;
        this.treatmentFee = treatmentFee;
        this.totalAmount = totalAmount;
    }

    // Generate Getters and Setters
}