package com.dentalclinic.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.dentalclinic.model.Bill;
import com.dentalclinic.util.DBConnection;

public class BillDAO {

    public boolean saveBill(Bill bill) {

        return false;
    }

    public Bill getBillByAppointmentId(int appointmentId) {

        return null;
    }

    public List<Bill> getAllBills() {

        return new ArrayList<>();
    }

}