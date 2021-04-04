package model;

import exceptions.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Car class
class CarTest {
    private Car car;
    private final int minDiscountHours = 3;
    private final double discountPercentage = 10.00;

    @BeforeEach
    void runBefore() {
        car = new Car("ABC123", "10:10", "10-10-2020", 5.00);
    }

    @Test
    void testGetLicenseNum() {
        assertEquals("ABC123", car.getLicenseNum());
    }

    @Test
    void testGetStartTime() {
        assertEquals("10:10", car.getStartTime());
    }

    @Test
    void testGetStartDate() {
        assertEquals("10-10-2020", car.getStartDate());
    }

    @Test
    void testGetRate() {
        assertEquals(5.00, car.getRate());
    }

    @Test
    void testGetHoursParkedSameDay() {
        Integer hoursParked1 = car.getHoursParked("20:09", "10-10-2020");
        assertEquals(9, hoursParked1);
        Integer hoursParked2 = car.getHoursParked("20:10", "10-10-2020");
        assertEquals(10, hoursParked2);
        Integer hoursParked3 = car.getHoursParked("20:11", "10-10-2020");
        assertEquals(10, hoursParked3);
    }

    @Test
    void testGetHoursParkedDiffDay() {
        Integer hoursParked1 = car.getHoursParked("09:09", "10-11-2020");
        assertEquals(22, hoursParked1);
        Integer hoursParked2 = car.getHoursParked("09:10", "10-11-2020");
        assertEquals(23, hoursParked2);
        Integer hoursParked3 = car.getHoursParked("09:11", "10-11-2020");
        assertEquals(23, hoursParked3);
    }

    @Test
    void testGetTotalCostZeroHour() {

        Double totalCost = car.getTotalCost("11:09", "10-10-2020",minDiscountHours, discountPercentage);
        assertEquals(5.00, totalCost);
    }

    @Test
    void testGetTotalCostMoreHourNoDiscount() {

        Double totalCost1 = car.getTotalCost("11:10", "10-10-2020",minDiscountHours, discountPercentage);
        Double totalCost2 = car.getTotalCost("13:09", "10-10-2020",minDiscountHours, discountPercentage);
        assertEquals(5.00, totalCost1);
        assertEquals(10.00, totalCost2);
    }

    @Test
    void testGetTotalCostMoreHourWithDiscount() {

        Double totalCost = car.getTotalCost("13:10", "10-10-2020",minDiscountHours, discountPercentage);
        assertEquals(13.5, totalCost);
    }

    @Test
    void testValidateStringsValid() {
       try {
           car.validate();
       } catch (InvalidInputException e) {
           fail("InvalidInputException should not be thrown");
       }
    }

    @Test
    void testValidateTimeValuesInValid() {
        Car car = new Car("ABC123", "AA:AA", "10-10-2020", 5.00);
        try {
            car.validate();
            fail("InvalidInputException should have been thrown.");
        } catch (InvalidInputException invalidInputException) {
            // expected
        }
    }

    @Test
    void testValidateDateValuesInValid() {
        Car car = new Car("ABC123", "10:10", "AA-AA-AAAA", 5.00);
        try {
            car.validate();
            fail("InvalidInputException should have been thrown.");
        } catch (InvalidInputException invalidInputException) {
            // expected
        }
    }

    @Test
    void testValidateColonInValid() {
        car = new Car("ABC123", "10A10", "10-10-2020", 5.00);
        try {
            car.validate();
            fail("InvalidInputException should have been thrown.");
        } catch (InvalidInputException invalidInputException) {
            // expected
        }
    }

    @Test
    void testValidateFirstHyphenInValid() {
        car = new Car("ABC123", "10:10", "10A10-2020", 5.00);
        try {
            car.validate();
            fail("InvalidInputException should have been thrown.");
        } catch (InvalidInputException invalidInputException) {
            // expected
        }
    }

    @Test
    void testValidateSecondHyphenInValid() {
        car = new Car("ABC123", "10:10", "10-10A2020", 5.00);
        try {
            car.validate();
            fail("InvalidInputException should have been thrown.");
        } catch (InvalidInputException invalidInputException) {
            // expected
        }
    }
}