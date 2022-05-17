package com.rental.model;

import com.rental.model.entity.Phone;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@Builder
@ToString
public class User {
	
	@NonNull private final String userId;
	@NonNull private final String userName;
	@NonNull private final Phone mobileNumber;
	@Builder.Default final String drivingLicenseID = "XXX-XXXX-XXXX";
	
}
