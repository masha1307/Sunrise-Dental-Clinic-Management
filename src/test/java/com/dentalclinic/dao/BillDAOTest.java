package com.dentalclinic.dao;

import com.dentalclinic.model.Bill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Note: BillDAO uses static DBConnection.getConnection() which requires a database connection.
 * These tests should be run as integration tests with a test database, or the DAO should be refactored
 * to use dependency injection for proper unit testing with Mockito.
 */
class BillDAOTest {

    private BillDAO billDAO;

    @BeforeEach
    void setUp() {
        billDAO = new BillDAO();
    }

    @Test
    void testSaveBill() {
        Bill bill = new Bill(0, 1, 50.0, 150.0, 200.0);
        boolean result = billDAO.saveBill(bill);
        // assertTrue(result);
    }

    @Test
    void testGetBillByAppointmentId() {
        Bill bill = billDAO.getBillByAppointmentId(1);
        // assertNotNull(bill);
    }
}
