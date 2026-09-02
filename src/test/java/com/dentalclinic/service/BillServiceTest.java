package com.dentalclinic.service;

import com.dentalclinic.dao.BillDAO;
import com.dentalclinic.model.Bill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BillServiceTest {

    @Mock
    private BillDAO billDAO;

    @InjectMocks
    private BillService billService;

    @BeforeEach
    void setUp() {
        billService = new BillService(billDAO);
    }

    @Test
    void testCalculateBill() {
        double consultationFee = 50.0;
        double treatmentFee = 150.0;
        double expected = 200.0;

        double result = billService.calculateBill(consultationFee, treatmentFee);

        assertEquals(expected, result, 0.001);
    }

    @Test
    void testCalculateBill_ZeroFees() {
        double result = billService.calculateBill(0.0, 0.0);

        assertEquals(0.0, result, 0.001);
    }

    @Test
    void testSaveBill_Success() {
        Bill bill = new Bill(1, 10, 50.0, 150.0, 200.0);
        when(billDAO.saveBill(any(Bill.class))).thenReturn(true);

        boolean result = billService.saveBill(bill);

        assertTrue(result);
        verify(billDAO).saveBill(bill);
    }

    @Test
    void testSaveBill_NullBill() {
        boolean result = billService.saveBill(null);

        assertFalse(result);
        verify(billDAO, never()).saveBill(any(Bill.class));
    }

    @Test
    void testSaveBill_Failure() {
        Bill bill = new Bill(1, 10, 50.0, 150.0, 200.0);
        when(billDAO.saveBill(any(Bill.class))).thenReturn(false);

        boolean result = billService.saveBill(bill);

        assertFalse(result);
        verify(billDAO).saveBill(bill);
    }

    @Test
    void testGetBillByAppointmentId_Success() {
        Bill bill = new Bill(1, 10, 50.0, 150.0, 200.0);
        when(billDAO.getBillByAppointmentId(10)).thenReturn(bill);

        Bill result = billService.getBillByAppointmentId(10);

        assertNotNull(result);
        assertEquals(1, result.getBillId());
        assertEquals(10, result.getAppointmentId());
        verify(billDAO).getBillByAppointmentId(10);
    }

    @Test
    void testGetBillByAppointmentId_NotFound() {
        when(billDAO.getBillByAppointmentId(999)).thenReturn(null);

        Bill result = billService.getBillByAppointmentId(999);

        assertNull(result);
        verify(billDAO).getBillByAppointmentId(999);
    }
}
