package com.example.JobSupportBackend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JobSupportBackend.entity.Skills;
import com.example.JobSupportBackend.entity.Users;
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
		Users user = userRepository.findByEmail(userEmail);
		if(user!=null) {
			skills.forEach(skill->{
				skill.setUsers(user);
				skillsRepository.save(skill);
			});
		}else {
			throw new InvalidIdException("Email not found..!!"+userEmail);
		}
	}

	@Override
	public Skills findByName(String skillName) {
		Skills skills = skillsRepository.findByName(skillName);
		skillsRepository.delete(skills);
		return skills;
	}

}
