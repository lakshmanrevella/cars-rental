package com.rental.model;

import com.rental.model.entity.RentingDates;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Accessors;

@Builder
@Getter
@Accessors(fluent = true)
@ToString
public class Booking 
{
	@NonNull private final String bookingId;
	@NonNull private final String userId;
	@NonNull private final String carId;
	@NonNull private final RentingDates rentedDate;
	@NonNull private final Integer amountChargable;

}
