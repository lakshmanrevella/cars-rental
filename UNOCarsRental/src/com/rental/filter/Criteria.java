package com.rental.filter;

import com.rental.model.entity.RentingDates;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

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
