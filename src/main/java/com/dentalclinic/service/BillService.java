package com.dentalclinic.service;

import com.dentalclinic.dao.BillDAO;
import com.dentalclinic.model.Bill;

public class BillService {


    private BillDAO billDAO;


    public BillService() {

        billDAO = new BillDAO();

    }



    // Method Overloading Example 1
    public double calculateBill(double consultationFee) {

        return consultationFee;

    }



    // Method Overloading Example 2
    public double calculateBill(double consultationFee,
                                double treatmentFee) {


        return consultationFee + treatmentFee;

    }



    // Method Overloading Example 3
    public double calculateBill(double consultationFee,
                                double treatmentFee,
                                double discount) {


        double total = consultationFee + treatmentFee;


        return total - discount;

    }



    public boolean saveBill(Bill bill) {


        if (bill == null) {

            return false;

        }


        return billDAO.saveBill(bill);

    }

}