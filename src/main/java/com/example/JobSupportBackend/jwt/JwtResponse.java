package com.example.JobSupportBackend.jwt;

import com.example.JobSupportBackend.entity.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
	
	private Users user;
	private String jwtToken;
}
