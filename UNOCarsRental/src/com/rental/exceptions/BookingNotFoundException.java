package com.rental.exceptions;

public class BookingNotFoundException extends RuntimeException
{
	private String bookingId;
	
	public BookingNotFoundException(String bookingId)
	{
		this.bookingId=bookingId;
	}
	
	@Override
	public String getMessage() {
		return "Booking : "+bookingId+ " not found !!!";
	}

}
