package com.rental.exceptions;

public class CarAlreadyRegisteredException extends RuntimeException
{
	private String carId;
	
	public CarAlreadyRegisteredException(String carId)
	{
		this.carId=carId;
	}
	
	@Override
	public String getMessage() {
		return "Car : "+carId+ " already registered !!!";
	}

}
