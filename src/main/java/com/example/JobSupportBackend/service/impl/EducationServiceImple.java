package com.example.JobSupportBackend.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JobSupportBackend.entity.Education;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.exceptions.InvalidIdException;
import com.example.JobSupportBackend.repo.EducationRepository;
import com.example.JobSupportBackend.repo.UserRepository;
import com.example.JobSupportBackend.service.EducationService;

@Service
public class EducationServiceImple implements EducationService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EducationRepository educationRepository;

	@Override
	public User getUserByEmail(String email) {
		User user = userRepository.findByEmail(email);
		if(user!=null) {
			List<Education> educations = educationRepository.findByUserEmail(email);
			user.setEducation(educations);
		}
		return user;
	}


	@Override
	public void addEducations(String userEmail, Set<Education> educations) throws InvalidIdException {
		User user = userRepository.findByEmail(userEmail);
		if(user!=null) {
			educations.forEach(education->{
				education.setUser(user);
				educationRepository.save(education);
			});
		}else {
			throw new InvalidIdException("Email not found..!!"+userEmail);
		}
		
	}

}
