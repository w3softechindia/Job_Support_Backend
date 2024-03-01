package com.example.JobSupportBackend.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.JobSupportBackend.dto.EmployerInfo;
import com.example.JobSupportBackend.dto.Otherinfo;
import com.example.JobSupportBackend.dto.PersonalInfo;
import com.example.JobSupportBackend.dto.Register;
import com.example.JobSupportBackend.dto.UserDataDTO;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.exceptions.InvalidIdException;
import com.example.JobSupportBackend.service.CertificationService;
import com.example.JobSupportBackend.service.EducationService;
import com.example.JobSupportBackend.service.ExperienceService;
import com.example.JobSupportBackend.service.LanguageService;
import com.example.JobSupportBackend.service.PhotoService;
import com.example.JobSupportBackend.service.SkillsService;
import com.example.JobSupportBackend.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;

	@Autowired
	private SkillsService skillsService;

	@Autowired
	private EducationService educationService;

	@Autowired
	private LanguageService languageService;

	@Autowired
	private ExperienceService experienceService;

	@Autowired
	private PhotoService photoService;

	@Autowired
	private CertificationService certificationService;

	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody Register register) throws InvalidIdException, MessagingException {
		return new ResponseEntity<User>(userService.register(register), HttpStatus.CREATED);
	}

	@PutMapping("/verify/{email}/{otp}")
	public ResponseEntity<User> verifyAccount(@PathVariable String email, @PathVariable String otp) throws Exception {
		return new ResponseEntity<User>(userService.verifyAccount(email, otp), HttpStatus.OK);
	}

	@PutMapping("/regenerate-otp/{email}")
	public ResponseEntity<String> regenerateOtp(@PathVariable String email)
			throws MessagingException, InvalidIdException {
		return new ResponseEntity<>(userService.regenerateOtp(email), HttpStatus.OK);
	}

	@PutMapping("/update/{email}")
	public ResponseEntity<User> role(@PathVariable String email, @RequestParam String newRole) throws Exception {
		User user = userService.updateRole(email, newRole);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PutMapping("/persnolInfo/{email}")
	public ResponseEntity<User> personalInfo(@PathVariable String email, @RequestBody PersonalInfo personalInfo)
			throws Exception {
		return new ResponseEntity<User>(userService.updatePersonalInfo(personalInfo, email), HttpStatus.ACCEPTED);
	}
	
	
	@PostMapping("/upload/{email}")
	public ResponseEntity<String> uploadPhoto(@PathVariable String email, @RequestParam("file") MultipartFile file) {
	    try {
	        userService.updateUserImagePathAndStoreInDatabase(email, file);
	        return ResponseEntity.ok("Photo uploaded successfully for user with email: " + email);
	    } catch (IOException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error uploading photo: " + e.getMessage());
	    }
}

	@PutMapping("/otherInfo/{email}")
	public ResponseEntity<User> otherInfo(@PathVariable String email, @RequestBody Otherinfo otherinfo)
			throws Exception {
		return new ResponseEntity<User>(userService.otherinfo(otherinfo, email), HttpStatus.ACCEPTED);
	}

	@PostMapping("/addUserData/{email}")
	public ResponseEntity<User> addUserData(@PathVariable String email, @RequestBody UserDataDTO dataDTO) {
		try {
			if (dataDTO.getSkills() != null) {
				skillsService.addSkills(email, dataDTO.getSkills());
			}
			if (dataDTO.getEducations() != null) {
				educationService.addEducations(email, dataDTO.getEducations());
			}
			if (dataDTO.getCertifications() != null) {
				certificationService.addCertications(email, dataDTO.getCertifications());
			}
			if (dataDTO.getExperiences() != null) {
				experienceService.addExperience(email, dataDTO.getExperiences());
			}
			if (dataDTO.getLanguages() != null) {
				languageService.addLanguages(email, dataDTO.getLanguages());
			}

			User user = userService.getUserByEmail(email);
			return ResponseEntity.status(HttpStatus.CREATED).body(user);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Return an empty body or any
																						// necessary data
		}
	}

//	 @GetMapping("/getUser/{email}")
//	 public ResponseEntity<?> getUserByEmail(@PathVariable String email){
//		 User user = skillsService.getUserByEmail(email);
//		 if(user!=null) {
//			 return new ResponseEntity<>(user, HttpStatus.OK);
//		 }else
//		 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//   }

	
	    
}

	
	
	
	
	
	
	
	
	
	
	
