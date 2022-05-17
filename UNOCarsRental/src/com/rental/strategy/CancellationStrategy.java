package com.rental.strategy;

import com.rental.model.entity.RentingDates;

public interface CancellationStrategy {

	public boolean isCancellationAllowed(RentingDates dates);
}
