package com.example.JobSupportBackend.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JobSupportBackend.entity.Education;
import com.example.JobSupportBackend.entity.Users;
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
	public void addEducations(String userEmail, List<Education> educations) throws InvalidIdException {
		Users user = userRepository.findByEmail(userEmail);
		if(user!=null) {
			educations.forEach(education->{
				education.setUsers(user);
				educationRepository.save(education);
			});
		}else {
			throw new InvalidIdException("Email not found..!!"+userEmail);
		}
		
	}

}
