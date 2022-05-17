package com.rental.strategy;

import java.time.LocalDateTime;

import com.rental.model.entity.RentingDates;

public class DefaultCancellationStrategy implements CancellationStrategy{
	
	@Override
	public  boolean isCancellationAllowed(RentingDates dates)
	{
		LocalDateTime local = LocalDateTime.now();
		return dates.startDate().minusHours(24).compareTo(local)>=0;
	}

}
