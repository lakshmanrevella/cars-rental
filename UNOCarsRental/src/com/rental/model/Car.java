package com.rental.model;

import java.util.ArrayList;
import java.util.List;

import com.rental.filter.Criteria;
import com.rental.model.entity.RentingDates;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@Builder
@Data
@ToString
//@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
//@AllArgsConstructor( access = AccessLevel.PRIVATE)
public class Car {

	@NonNull
	private final String carId;
	@NonNull
	private final String registrationNumber;
	@NonNull
	private final String color;
	@NonNull
	private final String type;
	@NonNull
	private final Integer seatingCapactity;
	@NonNull
	private final Integer pricePerDay;

	private @Builder.Default List<RentingDates> bookedDates = new ArrayList<>();

	public static class CarBuilder{
		private CarBuilder bookedDates(List<RentingDates> bookedDates)
		{
			return this;
		}
	}

	/**
	 * Checks whether this car is available for booking or not,based on the criteria.
	 * 
	 * @param criteria
	 * @return 
	 */
	public boolean isMatching(Criteria criteria)
	{
		boolean isMatched = true;
		Integer fromPrice = criteria.fromPrice();
		Integer toPrice = criteria.toPrice();

		if(fromPrice !=null)
		{
			isMatched = isMatched && fromPrice <= this.pricePerDay;
		}

		if(toPrice!=null)
		{
			isMatched = isMatched && toPrice>=this.pricePerDay;
		}

		String color = criteria.color();
		if(color!=null)
		{
			isMatched = isMatched && color.equalsIgnoreCase(this.color);
		}

		Integer seating = criteria.seatingCapactity();
		if(seating!=null)
		{
			isMatched = isMatched && seating==this.seatingCapactity;
		}

		String type = criteria.type();
		if(type!=null)
		{
			isMatched = isMatched && type.equalsIgnoreCase(this.type);
		}

		RentingDates bdates = criteria.bookDates();
		if( criteria!=null)
		{
			isMatched = isMatched && !bookedDates.stream().anyMatch(b -> b.isOverlapping(bdates));
		}

		return isMatched;
	}

	public void addBookedDate(RentingDates bookDate) 
	{
		bookedDates.add(bookDate);
	}

	public void removeBookedDate(RentingDates bookDate) 
	{
		bookedDates.remove(bookDate);
	}


}
