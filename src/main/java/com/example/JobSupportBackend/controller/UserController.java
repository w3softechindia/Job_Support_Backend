package com.example.JobSupportBackend.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.JobSupportBackend.dto.Otherinfo;
import com.example.JobSupportBackend.dto.PersonalInfo;
import com.example.JobSupportBackend.dto.Register;
import com.example.JobSupportBackend.entity.Skills;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.service.SkillsService;
import com.example.JobSupportBackend.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SkillsService skillsService;

	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody Register register) {
		return new ResponseEntity<User>(userService.register(register), HttpStatus.CREATED);
	}

	@PutMapping("/update/{email}")
	public ResponseEntity<User> role(@PathVariable String email, @RequestParam String newRole) throws Exception {
		User user = userService.updateRole(email, newRole);
		return new ResponseEntity<>(user,HttpStatus.OK);
	}

	@PutMapping("/persnolInfo/{email}")
	public ResponseEntity<User> personalInfo(@PathVariable String email, @RequestBody PersonalInfo personalInfo)
			throws Exception {
		return new ResponseEntity<User>(userService.updatePersonalInfo(personalInfo, email), HttpStatus.ACCEPTED);
	}

	@PutMapping("/otherInfo/{email}")
	public ResponseEntity<User> otherInfo(@PathVariable String email, @RequestBody Otherinfo otherinfo)
			throws Exception {
		return new ResponseEntity<User>(userService.otherinfo(otherinfo, email), HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/{email}/skills")
	public ResponseEntity<?> addSkillsToUser(@PathVariable String email,@RequestBody Set<Skills> skills){
		try {
			skillsService.addSkills(email, skills);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	 
	 @GetMapping("/getUser/{email}")
	 public ResponseEntity<?> getUserByEmail(@PathVariable String email){
		 User user = skillsService.getUserByEmail(email);
		 if(user!=null) {
			 return new ResponseEntity<>(user, HttpStatus.OK);
		 }else
		 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	 }
}
