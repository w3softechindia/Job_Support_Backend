package com.example.JobSupportBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.JobSupportBackend.dto.Otherinfo;
import com.example.JobSupportBackend.dto.PersonalInfo;
import com.example.JobSupportBackend.dto.Register;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody Register register) {

		return new ResponseEntity<User>(userService.register(register), HttpStatus.CREATED);

	}

	@PutMapping("/update/{email}")
	public ResponseEntity<User> role(@PathVariable String email, @RequestBody User user) throws Exception {
		return new ResponseEntity<User>(userService.updateRole(user, email), HttpStatus.ACCEPTED);
	}

	@PutMapping("/persnolInfo/{email}")
	public ResponseEntity<User> personalInfo(@PathVariable String email, @RequestBody PersonalInfo personalInfo)
			throws Exception {
		return new ResponseEntity<User>(userService.personalInfo(personalInfo, email), HttpStatus.ACCEPTED);
	}

	@PutMapping("/otherInfo/{email}")
	public ResponseEntity<User> otherInfo(@PathVariable String email, @RequestBody Otherinfo otherinfo)
			throws Exception {
		return new ResponseEntity<User>(userService.otherinfo(otherinfo, email), HttpStatus.ACCEPTED);
	}

}
