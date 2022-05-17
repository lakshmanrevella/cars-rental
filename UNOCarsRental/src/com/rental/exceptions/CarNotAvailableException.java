package com.rental.exceptions;

import java.time.LocalDateTime;

public class CarNotAvailableException extends RuntimeException
{
	private LocalDateTime start;
	private LocalDateTime end;
	private String carId;
	
	public CarNotAvailableException(String carId,LocalDateTime start,LocalDateTime end)
	{
		this.start = start;
		this.end = end;
		this.carId=carId;
	}
	
	@Override
	public String getMessage() {
		return "Car="+carId+" is not available for this booking date :  ["+start+ " - "+end+"]";
	}

}
