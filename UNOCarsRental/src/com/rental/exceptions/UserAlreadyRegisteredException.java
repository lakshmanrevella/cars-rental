package com.rental.exceptions;

public class UserAlreadyRegisteredException extends RuntimeException
{
	private String userId;
	
	public UserAlreadyRegisteredException(String userId)
	{
		this.userId=userId;
	}
	
	@Override
	public String getMessage() {
		return "User : "+userId+ " is already registered!!!.";
	}

}
