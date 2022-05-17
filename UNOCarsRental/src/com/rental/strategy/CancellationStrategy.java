package com.rental.strategy;

import com.rental.model.entity.RentingDates;

/**
 * 
 * Provided interface to have multiple flavors of cancellations.
 * 
 * @author lakshman
 *
 */
public interface CancellationStrategy {

	public boolean isCancellationAllowed(RentingDates dates);
}
