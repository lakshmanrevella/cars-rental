package com.rental.exceptions;

public class DuplicateBookingIDException extends RuntimeException
{
	private String bookingId;
	
	public DuplicateBookingIDException(String bookingId)
	{
		this.bookingId=bookingId;
	}
	
	@Override
	public String getMessage() {
		return " Duplicate BookingId : "+bookingId+ " found !!";
	}

}
