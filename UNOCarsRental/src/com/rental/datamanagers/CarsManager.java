package com.rental.datamanagers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.rental.exceptions.CarAlreadyRegisteredException;
import com.rental.exceptions.CarNotFoundException;
import com.rental.filter.Criteria;
import com.rental.model.Car;
import com.rental.model.entity.RentingDates;

/**
 * Used to manage cars.
 * 
 * @author lakshman
 */
public class CarsManager {

	private HashMap<String,Car> carIdVsCar = new HashMap<>();

	private HashSet<String> bookedCars = new HashSet<String>();

	protected void addCar(Car car)
	{
		String key = car.getCarId();
		if(carIdVsCar.containsKey(key))
		{
			throw new CarAlreadyRegisteredException(key);
		}
		carIdVsCar.put(key, car);
	}

	protected List<Car> filtercars(Criteria condition) 
	{
		return carIdVsCar.values().stream().filter(c->c.isMatching(condition)).collect(Collectors.toList());
	}

	protected void addRentedDate(String carId, RentingDates bookDate)
	{
		validateCarId(carId);
		carIdVsCar.get(carId).addBookedDate(bookDate);
		bookedCars.add(carId);
	}


	protected void removeRentedDate(String carId, RentingDates bookDate)
	{
		validateCarId(carId);
		carIdVsCar.get(carId).removeBookedDate(bookDate);
	}

	protected Car getCar(String carId)
	{
		validateCarId(carId);
		return carIdVsCar.get(carId);
	}

	private void validateCarId(String carId)
	{
		if(!carIdVsCar.containsKey(carId))
		{
			throw new CarNotFoundException(carId);
		}
	}

	/**
	 * checks whether booking for specified car is possible or not.
	 * @param carId
	 * @param bookDates
	 * @return
	 */
	protected boolean isBookingValid(String carId,RentingDates bookDates)
	{
		return getCar(carId).isMatching(Criteria.builder().bookDates(bookDates).build());
	}

	protected List<String> getCarIdsList()
	{
		return carIdVsCar.keySet().stream().collect(Collectors.toList());
	}

	protected List<String> getBookedCarsIdList()
	{
		return bookedCars.stream().collect(Collectors.toList());
	}

}
