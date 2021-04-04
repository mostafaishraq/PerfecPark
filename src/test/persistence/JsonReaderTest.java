package persistence;

import model.Car;
import model.ParkingList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    // testReaderNonExistentFile() taken from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ParkingList pl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    // testReaderEmptyParkingList() taken from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Test
    void testReaderEmptyParkingList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyParkingList.json");
        try {
            ParkingList pl = reader.read();
            assertEquals(10, pl.getNumberEmptySlots());
            assertEquals(5.00, pl.getRate());
            assertEquals(3, pl.getMinDiscountHours());
            assertEquals(10.00, pl.getDiscountPercentage());
            List<Car> cars = pl.getCars();
            assertEquals(0, cars.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    // testReaderGeneralWorkRoom() taken from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralParkingList.json");
        try {
            ParkingList pl = reader.read();
            assertEquals(8, pl.getNumberEmptySlots());
            assertEquals(5.00, pl.getRate());
            assertEquals(3, pl.getMinDiscountHours());
            assertEquals(10.00, pl.getDiscountPercentage());
            List<Car> cars = pl.getCars();
            assertEquals(2, cars.size());
            checkCar("ABC123", "10:10", "10-10-2020", 4.50, cars.get(0));
            checkCar("XYZ321", "20:20", "10-20-2020", 5.50, cars.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
