package com.example.JobSupportBackend.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JobSupportBackend.entity.Experience;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.exceptions.InvalidIdException;
import com.example.JobSupportBackend.repo.ExperienceRepository;
import com.example.JobSupportBackend.repo.UserRepository;
import com.example.JobSupportBackend.service.ExperienceService;

@Service
public class ExperienceServiceImple implements ExperienceService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ExperienceRepository experienceRepository;

	@Override
	public User getUserByEmail(String email) {
		User user = userRepository.findByEmail(email);
		if(user!=null) {
			List<Experience> experiences = experienceRepository.findByUserEmail(email);
			user.setExperience(experiences);
		}
		return user;
	}

	@Override
	public void addExperience(String userEmail, Set<Experience> experiences) throws InvalidIdException {
		User user = userRepository.findByEmail(userEmail);
		if(user!=null) {
			experiences.forEach(experience->{
				experience.setUser(user);
				experienceRepository.save(experience);
			});
		}else {
			throw new InvalidIdException("Email not found..!!"+userEmail);
		}
		
	}

}
