package com.dentalclinic.dao;

import java.util.List;
import com.dentalclinic.model.Bill;

public interface BillDAO {

    boolean addBill(Bill bill);

    Bill getBillByAppointment(int appointmentId);

    List<Bill> getAllBills();

}