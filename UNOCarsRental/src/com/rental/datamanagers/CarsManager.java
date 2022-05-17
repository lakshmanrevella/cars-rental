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

public class CarsManager {

	private HashMap<String,Car> map = new HashMap<>();

	private HashSet<String> bookedCars = new HashSet<String>();

	protected void addCar(Car car)
	{
		String key = car.getCarId();
		if(map.containsKey(key))
		{
			throw new CarAlreadyRegisteredException(key);
		}
		map.put(key, car);
	}

	protected List<Car> filtercars(Criteria condition) 
	{
		return map.values().stream().filter(c->c.isMatching(condition)).collect(Collectors.toList());
	}

	protected void addRentedDate(String carId, RentingDates bookDate)
	{
		validateCarId(carId);
		map.get(carId).addBookedDate(bookDate);
		bookedCars.add(carId);
	}


	protected void removeRentedDate(String carId, RentingDates bookDate)
	{
		validateCarId(carId);
		map.get(carId).removeBookedDate(bookDate);
	}

	protected Car getCar(String carId)
	{
		validateCarId(carId);
		return map.get(carId);
	}

	private void validateCarId(String carId)
	{
		if(!map.containsKey(carId))
		{
			throw new CarNotFoundException(carId);
		}
	}

	protected boolean isBookingValid(String carId,RentingDates bookDates)
	{
		return getCar(carId).isMatching(Criteria.builder().bookDates(bookDates).build());
	}

	protected List<String> getCarIdsList()
	{
		return map.keySet().stream().collect(Collectors.toList());
	}

	protected List<String> getBookedCarsIdList()
	{
		return bookedCars.stream().collect(Collectors.toList());
	}

}
