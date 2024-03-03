package com.example.JobSupportBackend.jwt;

import com.example.JobSupportBackend.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
	
	private User user;
	private String jwtToken;
}
