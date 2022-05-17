package com.rental.datamanagers;

import java.net.URI;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.rental.datamanagers.BookingsManager;
import com.rental.datamanagers.CarsManager;
import com.rental.datamanagers.UsersManager;
import com.rental.filter.Criteria;
import com.rental.model.Booking;
import com.rental.model.Car;
import com.rental.model.User;
import com.rental.model.entity.RentingDates;

import lombok.NonNull;

public class RentalService {

	private CarsManager carManager ;
	private UsersManager usersManager;
	private BookingsManager bookingsManager;

	public RentalService(BookingsManager bM)
	{
		this.bookingsManager=bM;
		usersManager = bM.getUserManager();
		carManager = bM.getCarManager();
	}

	public void addCar(Car car)
	{
		carManager.addCar(car);
	}

	public void addUser(User user)
	{
		usersManager.createUser(user);
	}

	public List<Car> filterCars(Criteria condition)
	{
		return carManager.filtercars(condition);

	}
	
	public BookingsManager getBookingsManager()
	{
		return bookingsManager;
	}

	private Map<String,Long> estimatedPrice(List<Car> cars,RentingDates bookDates)
	{
		Map<String,Long> map = new HashMap<String, Long>();

		long hoursBetween = ChronoUnit.HOURS.between(bookDates.startDate(), bookDates.endDate());
		long multiplicationFactor = (hoursBetween/24)+ hoursBetween%24 > 0 ? 1:0;

		for(Car car :cars)
		{
			map.put(car.getCarId(), car.getPricePerDay()*multiplicationFactor);
		}

		return map;
	}
	
	public int estimatedBookingPrice(@NonNull String carId,@NonNull RentingDates bookDates)
	{
		Car car = carManager.getCar(carId);
		int hoursBetween = (int) ChronoUnit.HOURS.between(bookDates.startDate(), bookDates.endDate());
		int multiplicationFactor = (hoursBetween/24)+ hoursBetween%24 > 0 ? 1:0;
		
		return car.getPricePerDay()*multiplicationFactor;
	}

	public void bookCar(Booking booking)
	{
		bookingsManager.createBooking(booking);
	}

//	//TO-DO
//	public void pay()
//	{
//		try 
//		{
//			URI uri= new URI("http://localhost:8080/UNOCarsRental/");
//			java.awt.Desktop.getDesktop().browse(uri);
//			System.out.println("Web page opened in browser");
//
//		} 
//		catch(Exception e) 
//		{
//
//			e.printStackTrace();
//		}
//	}

	public boolean cancelBooking(String bookingId)
	{
		return bookingsManager.cancelBooking(bookingId);
	}
	
	public List<String> getUsersIdList()
	{
		return usersManager.getUserIdsList();
	}
	
	public User getUser(String userId)
	{
		return usersManager.getUser(userId);
	}
	
	public List<String> getCarsIdList()
	{
		return carManager.getCarIdsList();
	}
	
	public Car getCar(String carId)
	{
		return carManager.getCar(carId);
	}
	
	public List<String> getBookingIdsList()
	{
		return bookingsManager.getbookingIdsList();
	}
	
	public List<String> getBookedCarsIdList()
	{
		return carManager.getBookedCarsIdList();
	}
	
	public Booking getBooking(String bookingId)
	{
		return bookingsManager.getBooking(bookingId);
	}

}
