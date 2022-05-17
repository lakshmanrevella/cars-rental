
# UNO Car Rentals

Car Rentals allows a user to rent car for few day(s).
## Integrations
- Paypal Payments
## Project Requirements
- JDK 1.8
- lombok 1.18.24
- Apache tomcat 9
- Eclipse IDE
- Maven
- Paypal REST SDK
- Paypal sandbox account : To test making payment during developemnt.
- Paypal Developer account : To handle  car rental payments to paypal business account.


## Assumptions
- APIs are created in a way assuming all the calls will be made through UserInterface or FrondEndService.
- For testing purpose, payment integration is disabled by default. To enable it set property as follows:
```
System.setProperty("enable.payment.gateway", "true");
```
## API Reference

#### filterCars(Criteria)
 Filter cars based on given criteria and return list of cars.
- Criteria can be seating capacity, car type, starting price(per day), ending price(per day), renting date range and car color.

#### estimatedBookingPrice(carId,RentingDates)

Calculates estimated price for given car for renting dates.
- RentingDates takes start date and end date as input.

#### bookCar(Booking)
Rent a car for given booking configurations.
- Booking takes bookingId, userId, carId, RentingDate range and amount chargeable.

#### cancelBooking(BookingId)
Tries to cancel the booking and return the cancellation status.
- true indicates Cancellation Successfull.
- false indicates Cancellation Not Allowed




## Run Locally

Step1 :
    
     Clone the project using Eclipse IDE 
    
or 

    create a new dynamic web project with name "UNOCarsRental" and copy all java/html/.jsp/.xml files in respective folders.

Step2 : [Configure tomcat server](https://crunchify.com/step-by-step-guide-to-setup-and-install-apache-tomcat-server-in-eclipse-development-environment-ide/) to this project over port 8080

Step3 : Start tomcat server in eclipse.

Step4 : Install [lombok in eclipse](https://www.baeldung.com/lombok-ide#eclipse).

Step5 : Ensure JDK, maven and tomcat dependencies are present in project Build Path.

Step6 : Open Client.java and run as java-application using eclipse.




