package com.rental.datamanagers;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.rental.exceptions.*;
import com.rental.model.Booking;
import com.rental.strategy.CancellationStrategy;

import lombok.Getter;
import lombok.NonNull;

public class BookingsManager {

	@NonNull
	@Getter
	final UsersManager userManager;
	@NonNull
	@Getter
	final CarsManager carManager;

	@NonNull
	final  CancellationStrategy cancelStrategy;

	private Map<String,Booking> bookings = new HashMap<>();

	public  BookingsManager(UsersManager userManager,CarsManager carManager, CancellationStrategy cancelStrategy)
	{
		this.userManager = userManager;
		this.carManager = carManager;
		this.cancelStrategy = cancelStrategy;
	}

	protected void createBooking(Booking booking)
	{
		if(bookings.containsKey(booking.bookingId()))
		{
			throw new DuplicateBookingIDException(booking.bookingId());
		}
		
		if(!carManager.isBookingValid(booking.carId(), booking.rentedDate()))
		{
			throw new CarNotAvailableException(booking.carId(),booking.rentedDate().startDate(),booking.rentedDate().endDate());
		}

		userManager.addUserBooking(booking.userId(),booking.bookingId());
		carManager.addRentedDate(booking.carId(), booking.rentedDate());
		bookings.put(booking.bookingId(), booking);

		if(Boolean.getBoolean("enable.payment.gateway"))
		{
			try 
			{
				URI uri= new URI("http://localhost:8080/UNOCarsRental/");
				java.awt.Desktop.getDesktop().browse(uri);
				System.out.println("Web page opened in browser");

			} 
			catch(Exception e) 
			{

				e.printStackTrace();
			}
		}
	}

	/**
	 * @param bookingId
	 * @return true - if booking can be cancelled based on cancellation strategy, else false.
	 */
	protected boolean cancelBooking(String bookingId)
	{
		Booking booking = getBooking(bookingId);
		if(cancelStrategy.isCancellationAllowed(booking.rentedDate()))
		{
			bookings.remove(bookingId);
			userManager.removeBooking(booking.userId(),bookingId);
			carManager.removeRentedDate(booking.carId(), booking.rentedDate());
			//TO-DO : Initiate Refund Process
			return true;
		}
		return false;
	}
	
	protected List<String> getbookingIdsList()
	{
		return bookings.keySet().stream().collect(Collectors.toList());
	}

	protected Booking getBooking(String bookingId) 
	{
		Booking booking = bookings.get(bookingId);

		if(booking==null)
		{
			throw new BookingNotFoundException(bookingId);
		}
		return booking;
	}
}
