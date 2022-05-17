package com.rental.exceptions;

public class CarNotFoundException extends RuntimeException
{
	private String carID;
	
	public CarNotFoundException(String carID)
	{
		this.carID=carID;
	}
	
	@Override
	public String getMessage() {
		return "car : "+carID+ " is not found !!!";
	}

}
