package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the ParkingList class
public class ParkingListTest {
    ParkingList parkingList;
    Car car;

    @BeforeEach
    void runBefore() {
        parkingList = new ParkingList(10, 5.00, 3, 10.00);
        car = new Car("A", "10:10", "10-10-2020", 5.00);
    }

    @Test
    void testAddCarListNotFull() {
        for (int i = 0; i < 10; i++) {

            assertTrue(parkingList.addCar(car));
            assertEquals(i+1, 10 - parkingList.getNumberEmptySlots());
        }
    }

    @Test
    void testAddCarListFull() {
        for (int i = 0; i < 10; i++) {

            assertTrue(parkingList.addCar(car));
            assertEquals(i+1, 10 - parkingList.getNumberEmptySlots());
        }


        assertFalse(parkingList.addCar(car));
        assertEquals(10,10 - parkingList.getNumberEmptySlots());
    }

    @Test
    void testRemoveCarExists() {

        assertTrue(parkingList.addCar(car));
        assertEquals(1, 10 - parkingList.getNumberEmptySlots());

        assertTrue(parkingList.removeCar(car));
        assertEquals(0, 10 - parkingList.getNumberEmptySlots());

    }

    @Test
    void testRemoveCarDoesNotExist() {


        assertEquals(0, 10 - parkingList.getNumberEmptySlots());

        assertFalse(parkingList.removeCar(car));
        assertEquals(0, 10 - parkingList.getNumberEmptySlots());
    }

    @Test
    void testGetCarExists() {
        Car car1 = new Car("A", "10:10", "10-10-2020", 5.00);
        assertTrue(parkingList.addCar(car1));
        assertEquals(1, 10 - parkingList.getNumberEmptySlots());

        Car car2 = parkingList.getCar("A");

        assertEquals(car1.getLicenseNum(), car2.getLicenseNum());
        assertEquals(car1.getStartTime(), car2.getStartTime());
        assertEquals(car1.getStartDate(), car2.getStartDate());
        assertEquals(car1.getRate(), car2.getRate());
        assertEquals(1, 10 - parkingList.getNumberEmptySlots());
    }
    @Test
    void testGetCarDoesNotExist() {
        Car car1 = new Car("A", "10:10", "10-10-2020", 5.00);
        assertTrue(parkingList.addCar(car1));
        assertEquals(1, 10 - parkingList.getNumberEmptySlots());

        Car car2 = parkingList.getCar("B");

        assertNull(car2);

        assertEquals(1, 10 - parkingList.getNumberEmptySlots());
    }

    @Test
    void testSetRate() {
        parkingList.setRate(7);
        assertEquals(7.00, parkingList.getRate());
    }

    @Test
    void testSetMinDiscountHours() {
        parkingList.setMinDiscountHours(4);
        assertEquals(4, parkingList.getMinDiscountHours());
    }

    @Test
    void testSetDiscountPercentage() {
        parkingList.setDiscountPercentage(12.00);
        assertEquals(12.00, parkingList.getDiscountPercentage());
    }

    @Test
    void testGetCars() {

        assertTrue(parkingList.addCar(car));
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        assertEquals(cars,parkingList.getCars());
    }
}
