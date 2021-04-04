package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a parking list having maximum size, rate of charge, minimum hours for discount,
// discount percentage and a collection of cars parked
public class ParkingList implements Writable {
    private final List<Car> parkingList;    // list of parked cars
    private final Integer maxSize;          // maximum number of cars the parking can accommodate
    private Double rate;
    private Integer minDiscountHours;
    private Double discountPercentage;

    // REQUIRES: maxSize > 0
    // EFFECTS: constructs a list of cars parked with maximum allowed size of maxSize
    public ParkingList(Integer maxSize, Double rate, Integer minDiscountHours, Double discountPercentage) {
        parkingList = new ArrayList<>();
        this.maxSize = maxSize;
        this.rate = rate;
        this.minDiscountHours = minDiscountHours;
        this.discountPercentage = discountPercentage;
    }

    // MODIFIES: this
    // EFFECTS: sets the value of rate to the given parameter
    public void setRate(double rate) {
        this.rate = rate;
    }

    // MODIFIES: this
    // EFFECTS: sets the value of minDiscountHours to the given parameter
    public void setMinDiscountHours(int minDiscountHours) {
        this.minDiscountHours = minDiscountHours;
    }

    // MODIFIES: this
    // EFFECTS: sets the value of discountPercentage to the given parameter
    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }


    // MODIFIES: this
    // EFFECTS: if parking list is not full,
    //          - adds the car to the list and
    //          - returns true
    //          otherwise, returns false
    public boolean addCar(Car car) {
        if (getNumberEmptySlots() == 0) {
            return false;
        } else {
            parkingList.add(car);
            return true;
        }
    }

    // MODIFIES: this
    // EFFECTS: if the car exists in the list,
    //          - removes the car from the list
    //          - returns true
    //          otherwise, return false
    public boolean removeCar(Car car) {
        return parkingList.remove(car);
    }


    // EFFECTS: returns the number of empty parking spots
    public Integer getNumberEmptySlots() {
        return maxSize - parkingList.size();
    }

    // EFFECTS: returns the rate of charge of parking
    public Double getRate() {
        return rate;
    }

    // EFFECTS: returns the minimum hours of discount of parking
    public Integer getMinDiscountHours() {
        return minDiscountHours;
    }


    // EFFECTS: returns the discount percentage of parking
    public Double getDiscountPercentage() {
        return discountPercentage;
    }


    // EFFECTS: looks for car with given licenseNum and if found, returns the car record,
    //          otherwise, returns null
    public Car getCar(String licenseNum) {
        for (Car car: parkingList) {
            if (car.getLicenseNum().equals(licenseNum)) {
                return car;
            }
        }
        return null;
    }

    // EFFECTS: return the list of cars parked
    public List<Car> getCars() {
        return parkingList;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("cars", carsToJson());
        json.put("maxSize", maxSize);
        json.put("rate", rate);
        json.put("minDiscountHours", minDiscountHours);
        json.put("discountPercentage", discountPercentage);
        return json;
    }

    // EFFECTS: returns cars in this parking list as a JSON array
    private JSONArray carsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Car c : parkingList) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }

}

