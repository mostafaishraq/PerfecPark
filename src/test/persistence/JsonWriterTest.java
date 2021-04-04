package persistence;

import model.Car;
import model.ParkingList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest{

    // testWriterInvalidFile() taken from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    // testWriterEmptyParkingList() taken from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Test
    void testWriterEmptyParkingList() {
        try {
            ParkingList pl = new ParkingList(10, 5.00, 3, 10.00);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyParkingList.json");
            writer.open();
            writer.write(pl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyParkingList.json");
            pl = reader.read();
            assertEquals(10, pl.getNumberEmptySlots());
            assertEquals(5.00, pl.getRate());
            assertEquals(3, pl.getMinDiscountHours());
            assertEquals(10.00, pl.getDiscountPercentage());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    // testWriterGeneralParkingList() taken from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Test
    void testWriterGeneralParkingList() {
        try {
            ParkingList pl = new ParkingList(10, 5.00, 3, 10.00);
            pl.addCar(new Car("ABC123", "10:10", "10-10-2020", 4.50));
            pl.addCar(new Car("XYZ321", "20:20", "10-20-2020", 5.50));

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralParkingList.json");
            writer.open();
            writer.write(pl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralParkingList.json");
            pl = reader.read();
            assertEquals(8, pl.getNumberEmptySlots());
            assertEquals(5.00, pl.getRate());
            assertEquals(3, pl.getMinDiscountHours());
            assertEquals(10.00, pl.getDiscountPercentage());
            List<Car> cars = pl.getCars();
            assertEquals(2, cars.size());
            checkCar("ABC123", "10:10", "10-10-2020", 4.50, cars.get(0));
            checkCar("XYZ321", "20:20", "10-20-2020", 5.50, cars.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
