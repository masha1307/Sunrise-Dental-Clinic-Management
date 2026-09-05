package com.dentalclinic.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TreatmentTest {

    @Test
    void testDefaultConstructor() {
        Treatment treatment = new Treatment();
        assertEquals(0, treatment.getTreatmentId());
        assertNull(treatment.getTreatmentName());
        assertNull(treatment.getPrice());
        assertNull(treatment.getDurationMinutes());
    }

    @Test
    void testSettersAndGetters() {
        Treatment treatment = new Treatment();
        treatment.setTreatmentId(1);
        treatment.setTreatmentName("Teeth Cleaning");
        treatment.setPrice(50.0);
        treatment.setDurationMinutes("30");

        assertEquals(1, treatment.getTreatmentId());
        assertEquals("Teeth Cleaning", treatment.getTreatmentName());
        assertEquals(50.0, treatment.getPrice());
        assertEquals("30", treatment.getDurationMinutes());
    }

    @Test
    void testSetTreatmentId() {
        Treatment treatment = new Treatment();
        treatment.setTreatmentId(100);
        assertEquals(100, treatment.getTreatmentId());
    }

    @Test
    void testSetTreatmentName() {
        Treatment treatment = new Treatment();
        treatment.setTreatmentName("Root Canal");
        assertEquals("Root Canal", treatment.getTreatmentName());
    }

    @Test
    void testSetPrice() {
        Treatment treatment = new Treatment();
        treatment.setPrice(200.0);
        assertEquals(200.0, treatment.getPrice());
    }

    @Test
    void testSetDurationMinutes() {
        Treatment treatment = new Treatment();
        treatment.setDurationMinutes("60");
        assertEquals("60", treatment.getDurationMinutes());
    }

    @Test
    void testSetPriceNull() {
        Treatment treatment = new Treatment();
        treatment.setPrice(null);
        assertNull(treatment.getPrice());
    }

    @Test
    void testSetPriceZero() {
        Treatment treatment = new Treatment();
        treatment.setPrice(0.0);
        assertEquals(0.0, treatment.getPrice());
    }
}
