package com.rental.filter;

import com.rental.model.entity.RentingDates;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 
 * Criteria with which we could filter out the cars.
 * Setting 2 or more variables in this object,
 * would result in operation equal to AND in database.
 * 
 * @author lakshman
 *
 */
@Builder
@Getter
@Accessors(fluent = true)
public class Criteria 
{
	String color;
	String type;
	Integer seatingCapactity;
	Integer fromPrice;
	Integer toPrice;
	RentingDates bookDates;

}
