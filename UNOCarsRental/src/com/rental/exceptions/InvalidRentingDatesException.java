package com.rental.exceptions;

public class InvalidRentingDatesException extends RuntimeException
{
	private String start;
	private String end;
	
	public InvalidRentingDatesException(String start,String end)
	{
		this.start=start;
		this.end=end;
	}
	
	@Override
	public String getMessage() {
		return "Renting dates : "+start+" - "+end+ " shouldn't be less than day !!!";
	}

}
