package com.example.JobSupportBackend.controller;

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

import com.example.JobSupportBackend.dto.EmployerInfo;
import com.example.JobSupportBackend.dto.Otherinfo;
import com.example.JobSupportBackend.dto.PersonalInfo;
import com.example.JobSupportBackend.dto.Register;
import com.example.JobSupportBackend.dto.UserDataDTO;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.service.CertificationService;
import com.example.JobSupportBackend.service.EducationService;
import com.example.JobSupportBackend.service.ExperienceService;
import com.example.JobSupportBackend.service.LanguageService;
import com.example.JobSupportBackend.service.SkillsService;
import com.example.JobSupportBackend.service.UserService;

@RestController
public class UserController {

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
	private CertificationService certificationService;

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
	
//	@PostMapping("/addSkills/{email}/skills")
//	public ResponseEntity<?> addSkillsToUser(@PathVariable String email,@RequestBody Set<Skills> skills, ){
//		try {
//			skillsService.addSkills(email, skills);
//			return new ResponseEntity<>(HttpStatus.CREATED);
//		}catch(Exception e) {
//			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
//		}
//	}
	
	 @PostMapping("/addUserData/{email}")
	    public ResponseEntity<String> addUserData(@PathVariable String email, @RequestBody UserDataDTO dataDTO) {
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

	            return ResponseEntity.status(HttpStatus.CREATED).body("Data added successfully");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
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
	 
	 @PutMapping("/employerInfo/{email}")
		public ResponseEntity<User> employerInfo(@PathVariable String email, @RequestBody EmployerInfo employerInfo)
				throws Exception {
			return new ResponseEntity<User>(userService.employerInfo(employerInfo, email), HttpStatus.ACCEPTED);
		}
}
