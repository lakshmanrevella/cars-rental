package com.rental.datamanagers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.rental.exceptions.UserAlreadyRegisteredException;
import com.rental.exceptions.UserNotFoundException;
import com.rental.model.User;

import lombok.NonNull;

public class UsersManager {

	private Map<String,User> map = new HashMap<>();
	private Map<String,List<String>> userVsBookings = new HashMap<>();
	
	protected void createUser(@NonNull final User user)
	{
		String key = user.getUserId();
		if(map.containsKey(key))
		{
			throw new UserAlreadyRegisteredException(key);
		}
		map.put(user.getUserId(), user);
	}
	
	
	protected User getUser(String userId)
	{
		if(!map.containsKey(userId))
		{
			throw new UserNotFoundException(userId);
		}
		return map.get(userId);
	}


	protected void addUserBooking(String userId, String bookingId) 
	{
		if(userVsBookings.containsKey(userId))
		{
			userVsBookings.get(userId).add(bookingId);
		}
		else
		{
			List<String> list = new ArrayList<String>();
			list.add(bookingId);
			userVsBookings.put(userId, list);
		}
		
	}


	protected void removeBooking(String userId, String bookingId) 
	{
		userVsBookings.get(userId).remove(bookingId);
	}
	
	protected List<String> getUserIdsList()
	{
		return map.keySet().stream().collect(Collectors.toList());
	}
}
