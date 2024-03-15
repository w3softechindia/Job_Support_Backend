package com.example.JobSupportBackend.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Register {

	private String email;
	private String name;
	private String password;
	private String otp;
	private LocalDateTime otpGeneratedtime;
	private boolean verified;
}
