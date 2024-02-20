package com.example.JobSupportBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PersonalInfo {
	
	  
	private String firstname;
	private String lastname;
	private long phonenumber;
	

	private String dob;
	private String jobtitle;
	private String typeofjob;
	private String description;

	

}
