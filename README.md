# PerfectPark

I completed this project as a graded project work of CPSC 210 course at the University of British Columbia.

The application, called PerfectPark, is a car parking management application. 
The potential user is a staff member at the car parking facility. A simple overview of the model is described below:
- When a car enters the parking lot, the user inputs the car's license plate number, and
the time and date of the start of the car's parking period into the application. 
These are stored by the application as long as the car is parked in the parking lot.
- The car will be charged at an hourly rate. If a car parks for less than an hour,
it will be charged for an hour.
The rate can be changed by the user, 
but a car will pay the rate set when it was entering the parking lot. 
Thus, the rate for the car will be recorded when it enters the system 
so that future rate changes does not affect its cost.
- When a car exists the parking lot, the user inputs the car's license plate number, and 
the time and date of the end of the car's parking period into the application.
The number of hours is calculated using the start and end times/dates and subsequently, 
the total cost is calculated using the rate (this is the rate stored at the start of the car's parking period).
A discount can be added to the total cost if the number of hours parked 
is greater than a specific number of hours, which can be set and changed by the user. 
The discount percentage can also be set and changed by the user.  

As someone who is fond of cars and loves driving, I have been to several paid parking lots around Vancouver. 
When I returned to my hometown of Chittagong in Bangladesh due to online classes, 
I realised how poorly the parking lots in my hometown are managed compared to those in Vancouver. 
Thus, I got this idea of a simple parking management application that could help parking 
authorities in Chittagong in managing their parking facilities easily and efficiently.

## User Stories

- As a user, I want to be able to add a car record to my parking list when the car is entering the parking 
and parking is not full.
- As a user, I want to be able to remove a car record from the parking list 
and view its total cost when the car is leaving the parking.
- As a user, I want to be able to view how long a specific car has been parked for.
- As a user, I want to be able to view a list of the license numbers of the cars parked. 
- As a user, I want to be able to set and change the minimum number of hours for discount and the discount percentage.
- As a user, I want to be able to change the rate of charge (in $/hour).
- As a user, I want to be able to save the parking list (including its maximum capacity of parking, rate of charge, minimum
hours for discount, and the discount percentage) to a file. 
- As a user, I want to be able to load the parking list (including its maximum capacity of parking, rate of charge, minimum 
hours for discount, and the discount percentage) from the file. 

##Citation:
- Author: Paul Carter 
- Date: 17 October 2020
- Title of program/source code
- Type: Source Code
- Code version: 1.0
- Availability: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo 
