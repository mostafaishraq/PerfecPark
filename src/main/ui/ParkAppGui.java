package ui;

import exceptions.InvalidInputException;
import model.Car;
import model.ParkingList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;

// Represents the graphical user interface of the parking management application
public class ParkAppGui extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/parkinglist.json";
    private static final Color BACKGROUND_COLOR = new Color(94, 134, 132);

    private JFrame frame;
    private JPanel panel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JLabel label;
    private JTextField fieldMaxCapacity;
    private JTextField fieldRate;
    private JTextField fieldMinDiscountHour;
    private JTextField fieldDiscountPercentage;
    private JTextField fieldCarLicense;
    private JTextField fieldCarTime;
    private JTextField fieldCarDate;
    private JButton buttonLeft;
    private JButton buttonRight;
    private JButton buttonEntry;
    private JButton buttonExit;
    private JButton buttonViewHour;
    private JButton buttonViewList;
    private JButton buttonChangeRate;
    private JButton buttonChangeMinHour;
    private JButton buttonChangeDiscount;
    private JButton buttonMainMenu;
    private JButton buttonContinue;

    private ParkingList parkingList;
    private Double rate;
    private Integer minDiscountHours;
    private Double discountPercentage;
    private final JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private final JsonReader jsonReader = new JsonReader(JSON_STORE);
    private final Font font = new Font("Verdana", Font.PLAIN, 30);

    public static void main(String[] args) {
        new ParkAppGui();
    }

    // EFFECTS: runs the main menu
    public ParkAppGui() {
        mainMenu();
    }

    // MODIFIES: this
    // EFFECTS: displays the window for the main menu
    private void mainMenu() {
        makePanel();

        buttonLeft = new JButton("Load previous parking list");
        buttonLeft.setPreferredSize(new Dimension(200,50));
        buttonLeft.setActionCommand("loadList");
        buttonLeft.addActionListener(this);

        buttonRight = new JButton("Open new parking list");
        buttonRight.setPreferredSize(new Dimension(200,50));
        buttonRight.setActionCommand("newList");
        buttonRight.addActionListener(this);

        ImageIcon homeImage = new ImageIcon(getClass().getResource("HomeImage.png"));
        label = new JLabel(homeImage);

        topPanel.add(label);
        bottomPanel.add(buttonLeft);
        bottomPanel.add(buttonRight);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        makeFrame();
    }

    // MODIFIES: this
    // EFFECTS: displays the window for new parking list
    private void doNewParkingList() {
        makePanel();
        topPanel.setLayout(new GridLayout(5, 1));

        topPanelNewList();

        buttonLeft = new JButton("Go Back");
        buttonLeft.setActionCommand("backMainMenu");
        buttonLeft.addActionListener(this);
        buttonRight = new JButton("Create and Save Parking");
        buttonRight.setActionCommand("afterNew");
        buttonRight.addActionListener(this);

        bottomPanel.add(buttonLeft);
        bottomPanel.add(buttonRight);


        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        makeFrame();

    }

    // MODIFIES: this
    // EFFECTS: creates the top panel of new list window
    private void topPanelNewList() {


        JLabel label1 = new JLabel("1. maximum capacity of the parking: ");
        label1.setFont(font);
        JLabel label2 = new JLabel("2. rate of charge ($/hour): ");
        label2.setFont(font);
        JLabel label3 = new JLabel("3. minimum hour(s) for discount: ");
        label3.setFont(font);
        JLabel label4 = new JLabel("4. discount percentage: ");
        label4.setFont(font);
        fieldMaxCapacity = new JTextField(5);
        fieldRate = new JTextField(5);
        fieldMinDiscountHour = new JTextField(5);
        fieldDiscountPercentage = new JTextField(5);

        topPanel.add(label1);
        topPanel.add(fieldMaxCapacity);
        topPanel.add(label2);
        topPanel.add(fieldRate);
        topPanel.add(label3);
        topPanel.add(fieldMinDiscountHour);
        topPanel.add(label4);
        topPanel.add(fieldDiscountPercentage);
    }

    // MODIFIES: this
    // EFFECTS: displays the window for loading parking list
    private void doLoadParkingList() {
        try {
            parkingList = jsonReader.read();
            rate = parkingList.getRate();
            minDiscountHours = parkingList.getMinDiscountHours();
            discountPercentage = parkingList.getDiscountPercentage();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
        makePanel();

        buttonContinue = new JButton("Continue");
        buttonContinue.setActionCommand("menu");
        buttonContinue.addActionListener(this);

        topPanelLoadList();
        bottomPanel.add(buttonContinue);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        makeFrame();
    }

    // MODIFIES: this
    // EFFECTS: creates the top panel of load list window
    private void topPanelLoadList() {
        String labelString = "<html>Loaded previous parking list...<br><br>";
        labelString += "Number of empty spots left  = " + parkingList.getNumberEmptySlots() + "<br>";
        labelString += "Rate of charge = " + parkingList.getRate() + "<br>";
        labelString += "Minimum hours for discount = " + parkingList.getMinDiscountHours() + "<br>";
        labelString += "Discount percentage = " + parkingList.getDiscountPercentage() + "<br><br></html>";


        label = new JLabel(labelString);
        label.setFont(font);
        topPanel.add(label);
    }

    // MODIFIES: this
    // EFFECTS: creates the top panel of menu
    private void doMenu() {
        makePanel();
        panel.setLayout(new GridLayout(8, 2));

        JLabel label1 = new JLabel("Car Entry");
        label1.setFont(font);

        JLabel label2 = new JLabel("Car Exit");
        label2.setFont(font);

        JLabel label3 = new JLabel("View Hour(s) Parked");
        label3.setFont(font);

        JLabel label4 = new JLabel("View List");
        label4.setFont(font);

        JLabel label5 = new JLabel("Change Rate");
        label5.setFont(font);

        JLabel label6 = new JLabel("Change Minimum Hour(s) for Discount");
        label6.setFont(font);

        JLabel label7 = new JLabel("Change Discount Percentage");
        label7.setFont(font);

        JLabel label8 = new JLabel("Go to Main Menu");
        label8.setFont(font);

        buttonMainMenu = new JButton("Go to Main Menu");
        buttonsMenu();

        panelAddMenu(label1, label2, label3, label4, label5, label6, label7, label8);

        makeFrame();

    }

    // MODIFIES: this
    // EFFECTS: sets up the buttons for menu window
    private void buttonsMenu() {

        buttonEntry = new JButton("Enter Car");
        buttonEntry.setActionCommand("entry");
        buttonEntry.addActionListener(this);

        buttonExit = new JButton("Remove Car");
        buttonExit.setActionCommand("exit");
        buttonExit.addActionListener(this);

        buttonViewHour = new JButton("View Hour(s) Parked");
        buttonViewHour.setActionCommand("viewHour");
        buttonViewHour.addActionListener(this);

        buttonViewList = new JButton("View List");
        buttonViewList.setActionCommand("viewList");
        buttonViewList.addActionListener(this);

        buttonChangeRate = new JButton("Change Rate ($/hour)");
        buttonChangeRate.setActionCommand("changeRate");
        buttonChangeRate.addActionListener(this);

        buttonChangeMinHour = new JButton("Change Minimum Hour(s)");
        buttonChangeMinHour.setActionCommand("changeMinHour");
        buttonChangeMinHour.addActionListener(this);

        buttonChangeDiscount = new JButton("Change Discount Percentage");
        buttonChangeDiscount.setActionCommand("changeDiscount");
        buttonChangeDiscount.addActionListener(this);

        buttonMainMenu.setActionCommand("mainMenu");
        buttonMainMenu.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: adds components to panel for menu window
    private void panelAddMenu(JLabel l1, JLabel l2,JLabel l3,JLabel l4,JLabel l5,JLabel l6,JLabel l7,JLabel l8) {
        panel.add(l1);
        panel.add(buttonEntry);
        panel.add(l2);
        panel.add(buttonExit);
        panel.add(l3);
        panel.add(buttonViewHour);
        panel.add(l4);
        panel.add(buttonViewList);
        panel.add(l5);
        panel.add(buttonChangeRate);
        panel.add(l6);
        panel.add(buttonChangeMinHour);
        panel.add(l7);
        panel.add(buttonChangeDiscount);
        panel.add(l8);
        panel.add(buttonMainMenu);
    }

    // MODIFIES: this
    // EFFECTS: displays window for car entry
    private void doEntry() {
        makePanel();
        if (parkingList.getNumberEmptySlots() == 0) {
            label = new JLabel("<html>Sorry, parking is full!<br>Please ask the car to park somewhere else</html>");
            label.setFont(new Font("Verdana", Font.BOLD, 30));
            panel.add(label);
        } else {
            topPanel.setLayout(new GridLayout(3, 1));

            buttonLeft = new JButton("Go Back");
            buttonLeft.setActionCommand("menu");
            buttonLeft.addActionListener(this);

            buttonRight = new JButton("Add Car and Save Parking");
            buttonRight.setActionCommand("addCar");
            buttonRight.addActionListener(this);

            topPanelEntryOrExitOrView();
            bottomPanel.add(buttonLeft);
            bottomPanel.add(buttonRight);

            panel.add(topPanel, BorderLayout.NORTH);
            panel.add(bottomPanel, BorderLayout.SOUTH);

        }
        makeFrame();
    }


    // MODIFIES: this
    // EFFECTS: displays window for car exit
    private void doExit() {
        makePanel();

        topPanel.setLayout(new GridLayout(3, 1));

        buttonLeft = new JButton("Go Back");
        buttonLeft.setActionCommand("menu");
        buttonLeft.addActionListener(this);

        buttonRight = new JButton("Remove Car and Save Parking");
        buttonRight.setActionCommand("removeCar");
        buttonRight.addActionListener(this);

        topPanelEntryOrExitOrView();
        bottomPanel.add(buttonLeft);
        bottomPanel.add(buttonRight);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        makeFrame();
    }

    // MODIFIES: this
    // EFFECTS: displays window for exiting car's price
    private void doDisplayPrice(Car car, String endTime, String endDate) {
        Double totalCost = car.getTotalCost(endTime, endDate, minDiscountHours, discountPercentage);
        Double discP = 0.00;
        if (car.getHoursParked(endTime, endDate) >= parkingList.getMinDiscountHours()) {
            discP = parkingList.getDiscountPercentage();
        }
        makePanel();

        topPanel.setLayout(new GridLayout(2, 1));

        buttonContinue = new JButton("Continue");
        buttonContinue.setActionCommand("menu");
        buttonContinue.addActionListener(this);

        topPanelPrice(discP, totalCost);
        bottomPanel.add(buttonContinue);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        makeFrame();
    }

    // MODIFIES: this
    // EFFECTS: creates top panel of display price window
    private void topPanelPrice(Double discP, Double totalCost) {

        JLabel label1 = new JLabel("Discount has been applied of " + discP + "%.");
        JLabel label2 = new JLabel("The total cost for this car is $" + totalCost);
        label1.setFont(font);
        label2.setFont(font);

        topPanel.add(label1);
        topPanel.add(label2);
    }

    // MODIFIES: this
    // EFFECTS: displays window for car's hour(s) parked
    private void doViewHour() {
        makePanel();

        topPanel.setLayout(new GridLayout(3, 1));

        buttonLeft = new JButton("Go Back");
        buttonLeft.setActionCommand("menu");
        buttonLeft.addActionListener(this);

        buttonRight = new JButton("View Hour(s)");
        buttonRight.setActionCommand("parkedHour");
        buttonRight.addActionListener(this);

        topPanelEntryOrExitOrView();
        bottomPanel.add(buttonLeft);
        bottomPanel.add(buttonRight);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        makeFrame();
    }

    // MODIFIES: this
    // EFFECTS: creates top panel of entry, exit and view hour(s) windows
    private void topPanelEntryOrExitOrView() {

        JLabel label1 = new JLabel("Enter the license plate number of the car ");
        label1.setFont(font);
        JLabel label2 = new JLabel("Enter the current time as HH:MM (24-hour clock):");
        label2.setFont(font);
        JLabel label3 = new JLabel("Enter today's date as MM-DD-YYYY:");
        label3.setFont(font);

        fieldCarLicense = new JTextField(5);
        fieldCarTime = new JTextField(5);
        fieldCarDate = new JTextField(5);

        topPanel.add(label1);
        topPanel.add(fieldCarLicense);
        topPanel.add(label2);
        topPanel.add(fieldCarTime);
        topPanel.add(label3);
        topPanel.add(fieldCarDate);
    }

    // MODIFIES: this
    // EFFECTS: displays window for list of cars parked
    private void doViewList() {
        makePanel();

        List<Car> carList = parkingList.getCars();
        StringBuilder labelString = new StringBuilder("<html>");
        if (carList.isEmpty()) {
            labelString.append("No cars parked right now");
        }
        for (Car c : carList) {
            labelString.append("Car ");
            labelString.append(carList.indexOf(c) + 1);
            labelString.append(": ");
            labelString.append(c.getLicenseNum()).append("<br>");
        }
        labelString.append("</html>");

        label = new JLabel(labelString.toString());
        label.setFont(font);

        buttonContinue = new JButton("Continue");
        buttonContinue.setActionCommand("menu");
        buttonContinue.addActionListener(this);

        topPanel.add(label);
        bottomPanel.add(buttonContinue);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        makeFrame();
    }

    // MODIFIES: this
    // EFFECTS: displays window for changing rate
    private void doChangeRate() {
        makePanel();

        topPanel.setLayout(new GridLayout(1,1));

        label = new JLabel("Enter the new rate of charge ($/hour): ");
        label.setFont(font);
        fieldRate = new JTextField(5);
        buttonLeft = new JButton("Go Back");
        buttonLeft.setActionCommand("menu");
        buttonLeft.addActionListener(this);

        buttonRight = new JButton("Save Change");
        buttonRight.setActionCommand("setRate");
        buttonRight.addActionListener(this);

        topPanel.add(label);
        topPanel.add(fieldRate);
        bottomPanel.add(buttonLeft);
        bottomPanel.add(buttonRight);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        makeFrame();
    }

    // MODIFIES: this
    // EFFECTS: displays window for changing minimum hour(s) for discount
    private void doChangeMinHour() {
        makePanel();

        topPanel.setLayout(new GridLayout(1,1));

        label = new JLabel("<html>Enter the new minimum number of hour(s)<br>needed for discount: </html>");
        label.setFont(font);
        fieldMinDiscountHour = new JTextField(5);
        buttonLeft = new JButton("Go Back");
        buttonLeft.setActionCommand("menu");
        buttonLeft.addActionListener(this);

        buttonRight = new JButton("Save Change");
        buttonRight.setActionCommand("setMinHour");
        buttonRight.addActionListener(this);

        topPanel.add(label);
        topPanel.add(fieldMinDiscountHour);
        bottomPanel.add(buttonLeft);
        bottomPanel.add(buttonRight);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        makeFrame();
    }

    // MODIFIES: this
    // EFFECTS: displays window for discount percentage
    private void doChangeDiscount() {
        makePanel();

        topPanel.setLayout(new GridLayout(1,1));

        label = new JLabel("Enter the new discount percentage :");
        label.setFont(font);
        fieldDiscountPercentage = new JTextField(5);
        buttonLeft = new JButton("Go Back");
        buttonLeft.setActionCommand("menu");
        buttonLeft.addActionListener(this);

        buttonRight = new JButton("Save Change");
        buttonRight.setActionCommand("setDiscount");
        buttonRight.addActionListener(this);

        topPanel.add(label);
        topPanel.add(fieldDiscountPercentage);
        bottomPanel.add(buttonLeft);
        bottomPanel.add(buttonRight);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        makeFrame();
    }

    // EFFECTS: saves the parking list to file
    private void doSaveParkingList() {
        try {
            jsonWriter.open();
            jsonWriter.write(parkingList);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates frame from panel
    private void makeFrame() {
        frame = new JFrame();
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("PerfectPark");
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates panel
    private void makePanel() {
        panel = new JPanel(new BorderLayout());

        topPanel = new JPanel();
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        panel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        panel.setBackground(BACKGROUND_COLOR);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        playSound("button.wav");
        frame.dispose();
        String command = e.getActionCommand();
        if (command.equals("newList")) {
            doNewParkingList();
        } else {
            if (command.equals("loadList")) {
                doLoadParkingList();
                doSaveParkingList();
            } else if (command.equals("afterNew")) {
                actionAfterNew();
            } else if (command.equals("backMainMenu")) {
                mainMenu();
            } else {
                actionMenu(command);
                doSaveParkingList();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: creates new parking list
    private void actionAfterNew() {
        try {
            Integer maxCapacity = Integer.parseInt(fieldMaxCapacity.getText());
            rate = Double.parseDouble(fieldRate.getText());
            minDiscountHours = Integer.parseInt(fieldMinDiscountHour.getText());
            discountPercentage = Double.parseDouble(fieldDiscountPercentage.getText());
            parkingList = new ParkingList(maxCapacity, rate, minDiscountHours, discountPercentage);
            doSaveParkingList();
            doMenu();
        } catch (Exception e) {
            invalidInputLabelPanel();
            makeFrame();
        }
    }

    // EFFECTS: performs action for commands in menu window
    private void actionMenu(String command) {
        if ((command.equals("menu"))) {
            doMenu();
        } else if ((command.equals("entry"))) {
            doEntry();
        } else if ((command.equals("exit"))) {
            doExit();
        } else if ((command.equals("viewHour"))) {
            doViewHour();
        } else if ((command.equals("parkedHour"))) {
            actionParkedHour();
        } else if ((command.equals("viewList"))) {
            doViewList();
        } else if ((command.equals("addCar"))) {
            actionAddCar();
        } else if ((command.equals("removeCar"))) {
            actionRemoveCar();
        } else if ((command.equals("mainMenu"))) {
            mainMenu();
        } else {
            actionChange(command);
        }
    }

    // EFFECTS: performs action for commands in change windows
    private void actionChange(String command) {
        if ((command.equals("changeRate"))) {
            doChangeRate();
        } else if ((command.equals("changeMinHour"))) {
            doChangeMinHour();
        } else if ((command.equals("changeDiscount"))) {
            doChangeDiscount();
        } else if ((command.equals("setRate"))) {
            actionSetRate();
        } else if ((command.equals("setMinHour"))) {
            actionSetMinHour();
        } else if ((command.equals("setDiscount"))) {
            actionSetDiscount();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds car to parking list
    private void actionAddCar() {
        rate = parkingList.getRate();
        Car car = new Car(fieldCarLicense.getText(), fieldCarTime.getText(), fieldCarDate.getText(), rate);
        try {
            car.validate();
            parkingList.addCar(car);
            makePanel();

            label = new JLabel(fieldCarLicense.getText() + " has been added to a parking spot");
            label.setFont(font);

            buttonContinue = new JButton("Continue");
            buttonContinue.setActionCommand("menu");
            buttonContinue.addActionListener(this);

            topPanel.add(label);
            bottomPanel.add(buttonContinue);

            panel.add(topPanel, BorderLayout.NORTH);
            panel.add(bottomPanel, BorderLayout.SOUTH);
        } catch (InvalidInputException invalidInputException) {
            invalidInputLabelPanel();
        } finally {
            makeFrame();
        }
//        if (!car.validate()) {
//            invalidInputLabelPanel();
//        } else {
//            parkingList.addCar(car);
//            makePanel();
//            topPanel = new JPanel(new GridLayout(2, 1));
//            bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//
//            label = new JLabel(fieldCarLicense.getText() + " has been added to a parking spot");
//            label.setFont(font);
//
//            buttonContinue = new JButton("Continue");
//            buttonContinue.setActionCommand("menu");
//            buttonContinue.addActionListener(this);
//
//            topPanel.add(label);
//            bottomPanel.add(buttonContinue);
//
//            panel.add(topPanel, BorderLayout.NORTH);
//            panel.add(bottomPanel, BorderLayout.SOUTH);
//
//        }
//        makeFrame();
    }

    // MODIFIES: this
    // EFFECTS: removes car from parking list
    private void actionRemoveCar() {
        String licenseNum = fieldCarLicense.getText();
        Car car = new Car(fieldCarLicense.getText(), fieldCarTime.getText(), fieldCarDate.getText(), rate);
        try {
            car.validate();
            car = parkingList.getCar(licenseNum);
            parkingList.removeCar(car);
            doDisplayPrice(car, fieldCarTime.getText(), fieldCarDate.getText());
        } catch (InvalidInputException invalidInputException) {
            invalidInputLabelPanel();
            makeFrame();
        }

//        if ((!car.validate()) || (parkingList.getCar(licenseNum) == null)) {
//            invalidInputLabelPanel();
//            makeFrame();
//        } else {
//            car = parkingList.getCar(licenseNum);
//            parkingList.removeCar(car);
//            doDisplayPrice(car, fieldCarTime.getText(), fieldCarDate.getText());
//        }
    }

    // MODIFIES: this
    // EFFECTS: displays window showing hours parked
    private void actionParkedHour() {
        String licenseNum = fieldCarLicense.getText();
        Car car = new Car(fieldCarLicense.getText(), fieldCarTime.getText(), fieldCarDate.getText(), rate);
        try {
            car.validate();
            makePanel();
            panel.setLayout(new GridLayout(2,1));
            car = parkingList.getCar(licenseNum);
            Integer totalHours = car.getHoursParked(fieldCarTime.getText(), fieldCarDate.getText());
            label = new JLabel("This car has been parked for " + totalHours + " hour(s).");
            label.setFont(font);
            panel.add(label);
            panel.add(buttonContinue);
        } catch (InvalidInputException invalidInputException) {
            invalidInputLabelPanel();
        } finally {
            makeFrame();
        }

//        if ((!car.validate()) || (parkingList.getCar(licenseNum) == null)) {
//            invalidInputLabelPanel();
//        } else {
//            makePanel();
//            panel.setLayout(new GridLayout(2,1));
//            car = parkingList.getCar(licenseNum);
//            Integer totalHours = car.getHoursParked(fieldCarTime.getText(), fieldCarDate.getText());
//            label = new JLabel("This car has been parked for " + totalHours + " hour(s).");
//            label.setFont(font);
//            panel.add(label);
//            panel.add(buttonContinue);
//        }
//        makeFrame();
    }

    // MODIFIES: this
    // EFFECTS: sets rate to input
    private void actionSetRate() {
        try {
            rate = Double.parseDouble(fieldRate.getText());
            parkingList.setRate(rate);
            doMenu();
        } catch (Exception e) {
            invalidInputLabelPanel();
            makeFrame();
        }
    }

    // MODIFIES: this
    // EFFECTS: sets minimum hour(s) for discount to input
    private void actionSetMinHour() {
        try {
            minDiscountHours = Integer.parseInt(fieldMinDiscountHour.getText());
            parkingList.setMinDiscountHours(minDiscountHours);
            doMenu();
        } catch (Exception e) {
            invalidInputLabelPanel();
            makeFrame();
        }
    }

    // MODIFIES: this
    // EFFECTS: sets discount percentage to input
    private void actionSetDiscount() {
        try {
            discountPercentage = Double.parseDouble(fieldDiscountPercentage.getText());
            parkingList.setDiscountPercentage(discountPercentage);
            doMenu();
        } catch (Exception e) {
            invalidInputLabelPanel();
            makeFrame();
        }
    }

    private void invalidInputLabelPanel() {
        JLabel invalidInputLabel = new JLabel("Invalid Input! Please Try Again...");
        invalidInputLabel.setFont(font);
        invalidInputLabel.setForeground(Color.RED);
        invalidInputLabel.setOpaque(true);
        invalidInputLabel.setBackground(Color.WHITE);
        panel.add(invalidInputLabel);
    }

    // EFFECTS: plays sound from file
    public void playSound(String soundName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource(soundName));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

}
