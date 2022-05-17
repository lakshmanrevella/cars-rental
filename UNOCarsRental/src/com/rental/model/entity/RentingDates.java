package com.rental.model.entity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.rental.exceptions.InvalidRentingDatesException;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class RentingDates {

	LocalDateTime startDate;
	LocalDateTime endDate;

	public RentingDates(LocalDateTime bookStartDate, LocalDateTime bookEndDate)
	{
		startDate = bookStartDate;
		endDate = bookEndDate;
		
		validate();
	}

	private void validate() 
	{
		if(ChronoUnit.HOURS.between(startDate,endDate) < 24)
		{
			throw new InvalidRentingDatesException(startDate.toString(),endDate.toString());
		}
	}

	public boolean isOverlapping(RentingDates bDate)
	{
		if(bDate==null)
			return false;
		
		if(this.startDate.compareTo(bDate.startDate) >= 0 && this.startDate.compareTo(bDate.endDate) <= 0)
		{
			return true;
		}
		
		if(bDate.startDate.compareTo(this.startDate) >= 0 && bDate.startDate.compareTo(this.endDate) <= 0)
		{
			return true;
		}

		if(this.endDate.compareTo(bDate.startDate) >= 0 && this.endDate.compareTo(bDate.endDate) <= 0)
		{
			return true;
		}
		
		if(bDate.endDate.compareTo(this.startDate) >= 0 && bDate.endDate.compareTo(this.endDate) <= 0)
		{
			return true;
		}
		
		return false;
	}
	
	@Override
	public String toString() 
	{
		return startDate.toString()+ " - "+endDate.toString();
	}

}
