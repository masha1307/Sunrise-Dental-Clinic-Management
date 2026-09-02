package com.dentalclinic.service;

import com.dentalclinic.dao.BillDAO;
import com.dentalclinic.model.Bill;

public class BillService {

    private BillDAO billDAO;

    public BillService() {
        billDAO = new BillDAO();
    }

    public BillService(BillDAO billDAO) {
        this.billDAO = billDAO;
    }

    public double calculateBill(double consultationFee, double treatmentFee) {
        return consultationFee + treatmentFee;
    }

    public boolean saveBill(Bill bill) {
        if (bill == null) {
            return false;
        }
        return billDAO.saveBill(bill);
    }

    public Bill getBillByAppointmentId(int appointmentId) {
        return billDAO.getBillByAppointmentId(appointmentId);
    }
}