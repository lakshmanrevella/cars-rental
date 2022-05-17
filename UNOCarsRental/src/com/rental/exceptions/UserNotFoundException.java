package com.rental.exceptions;

public class UserNotFoundException extends RuntimeException
{
	private String userId;
	
	public UserNotFoundException(String userId)
	{
		this.userId=userId;
	}
	
	@Override
	public String getMessage() {
		return "User : "+userId+ " is not registered !!!";
	}

}
