package com.dentalclinic.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BillTest {

    @Test
    void testDefaultConstructor() {
        Bill bill = new Bill();
        assertEquals(0, bill.getBillId());
        assertEquals(0, bill.getAppointmentId());
        assertEquals(0.0, bill.getConsultationFee());
        assertEquals(0.0, bill.getTreatmentFee());
        assertEquals(0.0, bill.getTotalAmount());
        assertNull(bill.getAppointmentDate());
        assertNull(bill.getPatientName());
        assertNull(bill.getAppointmentNumber());
    }

    @Test
    void testParameterizedConstructor() {
        Bill bill = new Bill(1, 10, 50.0, 150.0, 200.0);
        assertEquals(1, bill.getBillId());
        assertEquals(10, bill.getAppointmentId());
        assertEquals(50.0, bill.getConsultationFee());
        assertEquals(150.0, bill.getTreatmentFee());
        assertEquals(200.0, bill.getTotalAmount());
    }

    @Test
    void testSettersAndGetters() {
        Bill bill = new Bill();
        bill.setBillId(2);
        bill.setAppointmentId(11);
        bill.setConsultationFee(75.0);
        bill.setTreatmentFee(200.0);
        bill.setTotalAmount(275.0);
        bill.setAppointmentDate("2024-01-15");
        bill.setPatientName("John Doe");
        bill.setAppointmentNumber("APT-001");

        assertEquals(2, bill.getBillId());
        assertEquals(11, bill.getAppointmentId());
        assertEquals(75.0, bill.getConsultationFee());
        assertEquals(200.0, bill.getTreatmentFee());
        assertEquals(275.0, bill.getTotalAmount());
        assertEquals("2024-01-15", bill.getAppointmentDate());
        assertEquals("John Doe", bill.getPatientName());
        assertEquals("APT-001", bill.getAppointmentNumber());
    }

    @Test
    void testSetBillId() {
        Bill bill = new Bill();
        bill.setBillId(100);
        assertEquals(100, bill.getBillId());
    }

    @Test
    void testSetAppointmentId() {
        Bill bill = new Bill();
        bill.setAppointmentId(200);
        assertEquals(200, bill.getAppointmentId());
    }

    @Test
    void testSetConsultationFee() {
        Bill bill = new Bill();
        bill.setConsultationFee(100.0);
        assertEquals(100.0, bill.getConsultationFee());
    }

    @Test
    void testSetTreatmentFee() {
        Bill bill = new Bill();
        bill.setTreatmentFee(300.0);
        assertEquals(300.0, bill.getTreatmentFee());
    }

    @Test
    void testSetTotalAmount() {
        Bill bill = new Bill();
        bill.setTotalAmount(400.0);
        assertEquals(400.0, bill.getTotalAmount());
    }

    @Test
    void testSetAppointmentDate() {
        Bill bill = new Bill();
        bill.setAppointmentDate("2024-12-31");
        assertEquals("2024-12-31", bill.getAppointmentDate());
    }

    @Test
    void testSetPatientName() {
        Bill bill = new Bill();
        bill.setPatientName("Jane Smith");
        assertEquals("Jane Smith", bill.getPatientName());
    }

    @Test
    void testSetAppointmentNumber() {
        Bill bill = new Bill();
        bill.setAppointmentNumber("APT-999");
        assertEquals("APT-999", bill.getAppointmentNumber());
    }
}
