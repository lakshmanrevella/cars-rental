package com.rental.model.entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.rental.exceptions.PhoneNumberNotValid;

import lombok.ToString;

@ToString
public class Phone {

	private final String number;

	public Phone(String number)
	{
		validate(number);
		this.number = number;
	}

	private void validate(String number) 
	{
		if(number == null)
		{
			throw new PhoneNumberNotValid(number);
		}
		Pattern ptrn = Pattern.compile("[6-9][0-9]{9}");  
		//the matcher() method creates a matcher that will match the given input against this pattern  
		Matcher match = ptrn.matcher(number);  
		//returns a boolean value  
		if(!(match.find() && match.group().equals(number)))
		{
			throw new PhoneNumberNotValid(number);
		}

	}
	
	public String getNumber()
	{
		return number;
	}
	
	public static void main(String[] args) {
		new Phone(null);
	}
	
}
