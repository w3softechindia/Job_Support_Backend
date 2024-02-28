package com.example.JobSupportBackend.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JobSupportBackend.entity.Skills;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.exceptions.InvalidIdException;
import com.example.JobSupportBackend.repo.SkillsRepository;
import com.example.JobSupportBackend.repo.UserRepository;
import com.example.JobSupportBackend.service.SkillsService;

@Service
public class SkillsServiceImple implements SkillsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SkillsRepository skillsRepository;

	@Override
	public void addSkills(String userEmail, List<Skills> skills) throws InvalidIdException {
		User user = userRepository.findByEmail(userEmail);
		if(user!=null) {
			skills.forEach(skill->{
				skill.setUser(user);
				skillsRepository.save(skill);
			});
		}else {
			throw new InvalidIdException("Email not found..!!"+userEmail);
		}
	}

}
