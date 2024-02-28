package com.example.JobSupportBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Otherinfo {
	
	private String facebook;
	private String instagram;
	private String linkedin;

	private String persnolurl;
	private String address;
	private String city;
	private String state;
	private String postcode;
	private String postcodetype;
	private String documenttype;
	private String documentnumber;
}
