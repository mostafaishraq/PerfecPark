package persistence;

import model.Car;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    // checkCar() taken from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    protected void checkCar(String licenseNum, String startTime, String startDate, Double rate, Car car) {
        assertEquals(licenseNum, car.getLicenseNum());
        assertEquals(startTime, car.getStartTime());
        assertEquals(startDate, car.getStartDate());
        assertEquals(rate, car.getRate());
    }
}
