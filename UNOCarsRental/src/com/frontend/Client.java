package com.frontend;

import java.time.LocalDateTime;
import java.util.List;

import com.rental.datamanagers.BookingsManager;
import com.rental.datamanagers.CarsManager;
import com.rental.datamanagers.RentalService;
import com.rental.datamanagers.UsersManager;
import com.rental.exceptions.CarNotAvailableException;
import com.rental.filter.Criteria;
import com.rental.model.Booking;
import com.rental.model.Car;
import com.rental.model.User;
import com.rental.model.entity.Phone;
import com.rental.model.entity.RentingDates;
import com.rental.strategy.CancellationStrategy;
import com.rental.strategy.DefaultCancellationStrategy;

public class Client {

	public static void main(String[] args) 
	{
		UsersManager usersManager = new UsersManager();
		CarsManager carsManager = new CarsManager();
		CancellationStrategy cancelPolicy = new DefaultCancellationStrategy();
		BookingsManager bookManager = new BookingsManager(usersManager,carsManager,cancelPolicy);

		/**
		 * RentalService is the point of contact to client for all the major functionalities.
		 */
		RentalService service = new RentalService(bookManager);

		/* ---------------------------------- Creating Users ------------------------------------------*/

		User user = User.builder().userId("user1").userName("Lakshman").mobileNumber(new Phone("7978968198")).build();
		service.addUser(user);
		user = User.builder().userId("user2").userName("L akshman").mobileNumber(new Phone("7798968198")).build();
		service.addUser(user);
		user = User.builder().userId("user3").userName("La kshman").mobileNumber(new Phone("7789968198")).build();
		service.addUser(user);
		user = User.builder().userId("user4").userName("Lak shman").mobileNumber(new Phone("7798368198")).build();
		service.addUser(user);
		user = User.builder().userId("user5").userName("Laks hman").mobileNumber(new Phone("7798468198")).build();
		service.addUser(user);
		user = User.builder().userId("user6").userName("Laksh man").mobileNumber(new Phone("7798568198")).build();
		service.addUser(user);
		user = User.builder().userId("user7").userName("Lakshm an").mobileNumber(new Phone("7798668198")).build();
		service.addUser(user);

		/* ---------------------------------- Displaying created users ------------------------------------------*/
		List<String> users = service.getUsersIdList();

		System.out.print("Created users : ");
		for(String userId : users)
		{
			System.out.print(userId + ", ");
		}
		System.out.println();

		/* ---------------------------------- Creating Cars ------------------------------------------*/
		Car c = Car.builder().carId("car1").color("Black").pricePerDay(900).type("SUV").registrationNumber("TN19AL0922").seatingCapactity(6).build();
		service.addCar(c);
		c = Car.builder().carId("car2").color("Black").pricePerDay(1000).type("XL").registrationNumber("TN19AL3231").seatingCapactity(6).build();
		service.addCar(c);
		c = Car.builder().carId("car3").color("Red").pricePerDay(2000).type("SUV").registrationNumber("TN19AL4567").seatingCapactity(5).build();
		service.addCar(c);
		c = Car.builder().carId("car4").color("Blue").pricePerDay(1900).type("SUV").registrationNumber("TN19AL7321").seatingCapactity(6).build();
		service.addCar(c);
		c = Car.builder().carId("car5").color("White").pricePerDay(900).type("Mini").registrationNumber("TN19AL8421").seatingCapactity(6).build();
		service.addCar(c);
		c = Car.builder().carId("car6").color("Red").pricePerDay(1500).type("XL").registrationNumber("TN19AL1234").seatingCapactity(5).build();
		service.addCar(c);
		c = Car.builder().carId("car7").color("Red").pricePerDay(900).type("Mini").registrationNumber("TN19AL9101").seatingCapactity(6).build();
		service.addCar(c);

		/* ---------------------------------- Displaying created cars ------------------------------------------*/
		List<String> cars = service.getCarsIdList();

		System.out.println("-----------------------------------------------------");
		System.out.println("CREATED CARS : \n");
		for(String carId : cars)
		{
			System.out.println(carId + "->"+ service.getCar(carId));
		}
		System.out.println("-----------------------------------------------------");

		/* ---------------------------------- Filter car haing Red ------------------------------------------*/


		Criteria cri = Criteria.builder().color("Red").build();
		System.out.print("Filtering Cars having RED color  : ");
		for(Car car : service.filterCars(cri)) 
		{
			System.out.print(car.getCarId()+", ");
		}

		/* ---------------------------------- Filter cars having model XL ------------------------------------------*/


		cri = Criteria.builder().type("XL").build();
		System.out.print("\nFiltering Cars having XL model    : ");
		for(Car car : service.filterCars(cri))
		{
			System.out.print(car.getCarId()+", ");
		}

		/* ---------------------------------- Filter cars having price from 1400 ------------------------------------------*/


		cri = Criteria.builder().fromPrice(2000).build();
		System.out.print("\nFiltering Cars charging price over Rs 2000perday   : ");
		for(Car car : service.filterCars(cri))
		{
			System.out.print(car.getCarId()+", ");
		}

		/* ---------------------------------- Filter cars having seating Capacity 5 and price between 1200 & 2000 ------------------------------------------*/


		cri = Criteria.builder().fromPrice(1200).toPrice(2000).seatingCapactity(5).build();
		System.out.print("\nFiltering Cars having price between 1200 & 2000 and seating capacity 5   : ");
		for(Car car : service.filterCars(cri))
		{
			System.out.print(car.getCarId()+", ");
		}

		/* ----------------------------------user1 Booking car1 ------------------------------------------*/

		LocalDateTime start = LocalDateTime.of(2022,5,10,8,9,10);
		LocalDateTime end = LocalDateTime.of(2022,5,13,8,9,10);

		RentingDates bookDates = new RentingDates(start, end);
		String carId = "car1";
		int amountChargable = service.estimatedBookingPrice(carId, bookDates);
		Booking booking = Booking.builder().bookingId("booking1").carId(carId).userId("user1").rentedDate(bookDates).amountChargable(amountChargable).build();

		service.bookCar(booking);

		/* ----------------------------------user2 Booking car2 ------------------------------------------*/

		start = LocalDateTime.of(2022,5,14,8,9,10);
		end = LocalDateTime.of(2022,5,16,8,9,10);

		bookDates = new RentingDates(start, end);
		carId = "car2";
		amountChargable = service.estimatedBookingPrice(carId, bookDates);
		booking = Booking.builder().bookingId("booking2").carId(carId).userId("user2").rentedDate(bookDates).amountChargable(amountChargable).build();

		service.bookCar(booking);

		/* ----------------------------------user3 Booking car2 ------------------------------------------*/

		start = LocalDateTime.of(2022,5,17,8,9,10);
		end = LocalDateTime.of(2022,5,19,8,9,10);

		bookDates = new RentingDates(start, end);
		carId = "car2";
		amountChargable = service.estimatedBookingPrice(carId, bookDates);
		booking = Booking.builder().bookingId("booking3").carId(carId).userId("user3").rentedDate(bookDates).amountChargable(amountChargable).build();

		service.bookCar(booking);

		/* ----------------------------------user4 Booking car3 ------------------------------------------*/

		start = LocalDateTime.of(2022,5,15,8,9,10);
		end = LocalDateTime.of(2022,5,20,8,9,10);

		bookDates = new RentingDates(start, end);
		carId = "car3";
		amountChargable = service.estimatedBookingPrice(carId, bookDates);
		booking = Booking.builder().bookingId("booking4").carId(carId).userId("user4").rentedDate(bookDates).amountChargable(amountChargable).build();

		service.bookCar(booking);



		/* ----------------------------------Booked Cars ------------------------------------------*/

		System.out.println("\n-------------------------------------------------------------------------------------------");
		System.out.println( "Cabs are booked as below :\n");
		List<String> ids = service.getBookedCarsIdList();
		for(String id : ids)
		{
			System.out.println(id+ " -> "+service.getCar(id).getBookedDates());
		}
		System.out.println("-------------------------------------------------------------------------------------------");


		start = LocalDateTime.of(2022,5,16,8,9,10);
		end = LocalDateTime.of(2022,5,17,8,9,10);

		bookDates = new RentingDates(start, end);
		carId = "car2";
		amountChargable = service.estimatedBookingPrice(carId, bookDates);
		booking = Booking.builder().bookingId("booking5").carId(carId).userId("user4").rentedDate(bookDates).amountChargable(amountChargable).build();

		try {
			System.out.println(" Trying to book car2 on dates that it is already occupied !\n");
			service.bookCar(booking);
		}catch(CarNotAvailableException e)
		{
			System.out.println("EXCEPTION THROWN & CAUGHT");
			System.out.println(e.getMessage());
		}
		
		System.out.println("-------------------------------------------------------------------------------------------");


		/* ---------------------------------- Filter cars available for booking between 10 May to 13 May------------------------------------------*/


		start = LocalDateTime.of(2022,5,10,8,9,10);
		end = LocalDateTime.of(2022,5,15,7,9,10);
		bookDates = new RentingDates(start, end);
		cri = Criteria.builder().bookDates(bookDates).build();
		System.out.println("Filter cars available for booking between 10 May to 13 May   : \n");
		for(Car car : service.filterCars(cri))
		{
			System.out.print(car.getCarId()+", ");
		}
		System.out.println("\n---------------------------------Booking Cancellation cases----------------------------------------------------------");
		
		System.out.println("CASE1 (> 24 hours):\n");
		start = LocalDateTime.of(2022,5,19,8,9,10);
		end = LocalDateTime.of(2022,5,21,8,9,10);

		bookDates = new RentingDates(start, end);
		carId = "car6";
		String bookId = "booking6";
		amountChargable = service.estimatedBookingPrice(carId, bookDates);
		booking = Booking.builder().bookingId(bookId).carId(carId).userId("user5").rentedDate(bookDates).amountChargable(amountChargable).build();

		service.bookCar(booking);
		
		System.out.println("Booking successful : "+service.getBooking(bookId));
		System.out.println( "TRYING TO CANCEL ABOVE BOOKING ");
		System.out.println("    Car BookedDates before cancellation : "+service.getCar(carId).getBookedDates());
		System.out.println("     Cancellation status : "+(service.cancelBooking(bookId) ? "SUCCESS" : "FAILURE"));
		System.out.println("    Car BookedDates after cancellation : "+service.getCar(carId).getBookedDates());
		
		
		System.out.println("\nCASE2 (< 24 hours):\n");
		start = LocalDateTime.of(2022,5,16,8,9,10);
		end = LocalDateTime.of(2022,5,26,8,9,10);

		bookDates = new RentingDates(start, end);
		carId = "car5";
		bookId = "booking7";
		amountChargable = service.estimatedBookingPrice(carId, bookDates);
		booking = Booking.builder().bookingId(bookId).carId(carId).userId("user5").rentedDate(bookDates).amountChargable(amountChargable).build();

		service.bookCar(booking);
		
		System.out.println("Booking successful : "+service.getBooking(bookId));
		System.out.println( "TRYING TO CANCEL ABOVE BOOKING ");
		System.out.println("    Car BookedDates before cancellation : "+service.getCar(carId).getBookedDates());
		System.out.println("     Cancellation status : "+(service.cancelBooking(bookId) ? "SUCCESS" : "FAILURE"));
		System.out.println("    Car BookedDates after cancellation : "+service.getCar(carId).getBookedDates());
		
		System.out.println("-------------------------------------------------------------------------------------------");
		

		System.out.println( " \n ######################## UNCOMMENT BELOW CODE TO CHECK PAYMENT INTEGRATION BEHAVIOUR ######################### \n\n\n\n\n");
		
		
		System.setProperty("enable.payment.gateway", "true");
		
		start = LocalDateTime.of(2022,5,16,8,9,10);
		end = LocalDateTime.of(2022,5,26,8,9,10);

		bookDates = new RentingDates(start, end);
		carId = "car6";
		bookId = "booking8";
		amountChargable = service.estimatedBookingPrice(carId, bookDates);
		booking = Booking.builder().bookingId(bookId).carId(carId).userId("user5").rentedDate(bookDates).amountChargable(amountChargable).build();

		service.bookCar(booking);
		
	}
}
