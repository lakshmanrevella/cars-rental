package com.rental.exceptions;

public class PhoneNumberNotValid extends RuntimeException
{
	private String number;
	
	public PhoneNumberNotValid(String number)
	{
		this.number=number;
	}
	
	@Override
	public String getMessage() {
		return "Phone number : "+number+ " is invalid !!! ";
	}

}
