package ui;

import model.Car;
import model.ParkingList;

import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

//Represents a parking management application
public class ParkAppConsoleBased {
    private static final String JSON_STORE = "./data/parkinglist.json";
    private ParkingList parkingList;
    private Scanner input;
    private Double rate;
    private Integer minDiscountHours;
    private Double discountPercentage;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;

    // EFFECTS: constructs parking list and runs the parking management application
    public ParkAppConsoleBased() {
        input = new Scanner(System.in);
        parkingList = new ParkingList(0, 0.00, 0, 0.00);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runParkApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runParkApp() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayOptions();
            command = input.next();
            if (command.equals("9")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: initializes parking list and other variables from input
    private void init() {
        input = new Scanner(System.in);
        boolean keepGoing = true;
        System.out.println("\nWelcome To PerfectPark!");
        System.out.println("\n---REMEMBER: NO CAR IS PARKED FOR MORE THAN 24 HOURS---");
        System.out.println("---Cars parked for > 24 hours are automatically penalised and removed by authority---");
        while (keepGoing) {
            System.out.println("\nDo you want to load an existing parking list? Enter 'y' for yes and 'n' for no:");
            String command = input.next();
            if (command.equals("y")) {
                doLoadParkingList();
                keepGoing = false;
            } else if (command.equals("n")) {
                displayInitialOptions();
                Integer maxCapacity = input.nextInt();
                rate = input.nextDouble();
                minDiscountHours = input.nextInt();
                discountPercentage = input.nextDouble();
                parkingList = new ParkingList(maxCapacity, rate, minDiscountHours, discountPercentage);
                keepGoing = false;
            } else {
                System.out.println("Selection not valid...");
            }
        }
    }

    // EFFECTS: displays initial menu of options to enter
    private void displayInitialOptions() {
        System.out.println("\nTo get started, please input the following in the order stated:");
        System.out.println("\t1. maximum capacity of the parking");
        System.out.println("\t2. rate of charge ($/hour)");
        System.out.println("\t3. minimum hour(s) for discount");
        System.out.println("\t4. discount percentage");
    }

    // EFFECTS: displays menu of options to enter
    private void displayOptions() {
        System.out.println("\nPlease choose from the following:");
        System.out.println("\t1 -> to enter a car entering the parking.");
        System.out.println("\t2 -> to remove a car exiting the parking.");
        System.out.println("\t3 -> to check how long a car has been parked");
        System.out.println("\t4 -> to see the list of cars parked");
        System.out.println("\t5 -> to change the rate");
        System.out.println("\t6 -> to change minimum parking time for discount");
        System.out.println("\t7 -> to change the discount percentage");
        System.out.println("\t8 -> to save the parking list to file");
        System.out.println("\t9 -> to quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        switch (command) {
            case "1": doEntry();
                break;
            case "2": doExit();
                break;
            case "3": doParkedHours();
                break;
            case "4": doShowList();
                break;
            case "5": doChangeRate();
                break;
            case "6": doChangeMinDiscountHours();
                break;
            case "7": doChangeDiscountPercentage();
                break;
            case "8": doSaveParkingList();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: enters a car record into parking list
    private void doEntry() {
        if (parkingList.getNumberEmptySlots() == 0) {
            System.out.println("Sorry, parking is full! Please ask the car to park somewhere else.");
        } else {
            System.out.println("Enter the license plate number of the car that is entering:");
            String licenseNum = input.next();
            System.out.println("Enter the current time as HH:MM (24-hour clock):");
            String startTime = input.next();
            System.out.println("Enter today's date as MM-DD-YYYY:");
            String startDate = input.next();

            Car car = new Car(licenseNum, startTime, startDate, rate);

            parkingList.addCar(car);

            System.out.println("Car has been added to a parking spot.");
        }
    }

    /// MODIFIES: this
    // EFFECTS: removes a car record from parking list and shows total price
    private void doExit() {
        boolean keepGoing = true;
        String licenseNum = null;
        while (keepGoing) {
            System.out.println("Enter the license plate number of the car that is exiting:");
            licenseNum = input.next();
            
            if (parkingList.getCar(licenseNum) == null) {
                System.out.println("Sorry, there is no car with this license plate! Please try again.");
            } else {
                keepGoing = false;
            }
        }
        System.out.println("Enter the current time as HH:MM (24-hour clock):");
        String endTime = input.next();
        System.out.println("Enter today's date as MM-DD-YYYY:");
        String endDate = input.next();

        Car car = parkingList.getCar(licenseNum);
        displayPrice(car, endTime, endDate);
        parkingList.removeCar(car);
        System.out.println("Car has been removed from the parking spot.");
    }

    //EFFECTS: displays the number of hours a car with given license plate has been parked for
    public void doParkedHours() {
        boolean keepGoing = true;
        String licenseNum = null;
        while (keepGoing) {
            System.out.println("Enter the license plate number of the car:");
            licenseNum = input.next();

            if (parkingList.getCar(licenseNum) == null) {
                System.out.println("Sorry, there is no car with this license plate! Please try again.");
            } else {
                keepGoing = false;
            }
        }
        System.out.println("Enter the current time as HH:MM (24-hour clock):");
        String endTime = input.next();
        System.out.println("Enter today's date as MM-DD-YYYY:");
        String endDate = input.next();

        Car car = parkingList.getCar(licenseNum);
        Integer totalHours = car.getHoursParked(endTime, endDate);
        System.out.println("This car has been parked for " + totalHours + " hour(s).");
    }


    public void doShowList() {
        List<Car> carList = parkingList.getCars();
        if (carList.isEmpty()) {
            System.out.println("No cars parked right now");
        }
        for (Car c : carList) {
            System.out.println("Car " + (carList.indexOf(c) + 1) + ": " + c.getLicenseNum());
        }
    }

    // MODIFIES: this
    // EFFECTS: changes the rate of charge to the user input value
    public void doChangeRate() {
        System.out.println("Enter the new rate of charge ($/hour):");
        rate = input.nextDouble();
        parkingList.setRate(rate);

        System.out.println("The rate has been set to " + rate + " $/hour");
    }

    // MODIFIES: this
    // EFFECTS: changes the minimum hours needed for discount to the user input value
    public void doChangeMinDiscountHours() {
        System.out.println("Enter the new minimum number of hour(s) needed for discount:");
        minDiscountHours = input.nextInt();
        parkingList.setMinDiscountHours(minDiscountHours);

        System.out.println("The minimum hour(s) for discount is set to " + minDiscountHours);
    }

    // MODIFIES: this
    // EFFECTS: changes the discount percentage to the user input value
    public void doChangeDiscountPercentage() {
        System.out.println("Enter the new discount percentage:");
        discountPercentage = input.nextDouble();
        parkingList.setDiscountPercentage(discountPercentage);

        System.out.println("The discount percentage is set to " + discountPercentage + "%.");
    }

    // EFFECTS: saves the parking list to file
    private void doSaveParkingList() {
        try {
            jsonWriter.open();
            jsonWriter.write(parkingList);
            jsonWriter.close();
            System.out.println("Saved parking list to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads parking list from file
    private void doLoadParkingList() {
        try {
            parkingList = jsonReader.read();
            System.out.println("Loaded previous parking list from " + JSON_STORE);
            System.out.println("Number of empty spots left  = " + parkingList.getNumberEmptySlots());
            System.out.println("Rate of charge = " + parkingList.getRate());
            System.out.println("Minimum hours for discount = " + parkingList.getMinDiscountHours());
            System.out.println("Discount percentage = " + parkingList.getDiscountPercentage());
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: displays the total cost of car that is exiting parking
    private void displayPrice(Car car, String endTime, String endDate) {
        Double totalCost;

        if (car.getHoursParked(endTime, endDate) == 0) {
            totalCost = car.getRate();
        } else {
            totalCost = car.getHoursParked(endTime, endDate) * car.getRate();
        }



        if (car.getHoursParked(endTime, endDate) >= parkingList.getMinDiscountHours()) {
            totalCost = totalCost * ((100 - parkingList.getDiscountPercentage()) / 100);
            System.out.println("Discount has been applied of " + parkingList.getDiscountPercentage() + "%.");
        }

        System.out.println("The total cost for this car is $" + totalCost);
    }

}
