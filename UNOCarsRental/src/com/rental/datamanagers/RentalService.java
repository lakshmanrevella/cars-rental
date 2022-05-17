package com.rental.datamanagers;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.rental.filter.Criteria;
import com.rental.model.Booking;
import com.rental.model.Car;
import com.rental.model.User;
import com.rental.model.entity.RentingDates;

import lombok.NonNull;

/**
 * 
 * All the rental booking operations can be communicated to the backend through this class.
 * 
 * @author lakshman-3231
 *
 */
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

	/**
	 * If front-end needs an option to get estimated price for all cars between given date range, this api can be used.
	 * @param cars
	 * @param bookDates
	 * @return
	 */
	public Map<String,Long> estimatedPrice(List<Car> cars,RentingDates bookDates)
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
	
	/**
	 * calculates estimated booking price for given car over specified dates. 
	 * @param carId
	 * @param bookDates
	 * @return estimated booking price for given car over specified dates. 
	 */
	public int estimatedBookingPrice(@NonNull String carId,@NonNull RentingDates bookDates)
	{
		Car car = carManager.getCar(carId);
		int hoursBetween = (int) ChronoUnit.HOURS.between(bookDates.startDate(), bookDates.endDate());
		int multiplicationFactor = (hoursBetween/24)+ hoursBetween%24 > 0 ? 1:0;
		
		return car.getPricePerDay()*multiplicationFactor;
	}

	/**
	 * This api takes cares of entire booking process. Handles payments as well.
	 * By default payments is disabled, to enable set System property 'enable.payment.gateway' to 'true'.
	 * @param booking
	 */
	public void bookCar(Booking booking)
	{
		bookingsManager.createBooking(booking);
	}

	/**
	 * Takes care of booking cancellation
	 * @param bookingId
	 * @return true for successful cancellation, else false.
	 */
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
