/**
 * OrderDetail class - represents payment details.
 * @author Nam Ha Minh
 * @copyright https://codeJava.net
 */
package com.rental.payment;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Builder
@Getter
@Accessors(fluent = true)
public class OrderDetail {
	
	
	@NonNull private String carType;
	@NonNull private String startDate;
	@NonNull private String endDate;
	@NonNull private Float subtotal;
	@NonNull private Float tax;
	@NonNull private Float total;

}
