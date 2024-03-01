package com.example.JobSupportBackend.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {

	@Autowired
	private JwtServiceImplementation jwtServiceImplementation;

	@PostMapping("/freelancerLogin")
	public JwtResponse generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {

		return jwtServiceImplementation.createJwtToken(jwtRequest);
	}
	
	
	@PostMapping("/employerLogin")
	public JwtResponse generateToken2(@RequestBody JwtRequest jwtRequest) throws Exception {

		return jwtServiceImplementation.createJwtToken(jwtRequest);
	}
	
	
	@PostMapping("/adminLogin")
	public JwtResponse generateToken3(@RequestBody JwtRequest jwtRequest) throws Exception {

		return jwtServiceImplementation.createJwtToken(jwtRequest);
	}

}
