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
	public User getUserByEmail(String email) {
		User user = userRepository.findByEmail(email);
		if(user!=null) {
			List<Skills> userSkills = skillsRepository.findByUserEmail(email);
			user.setSkills(userSkills);
		}
		return user;
	}

	@Override
	public void addSkills(String userEmail, Set<Skills> skills) throws InvalidIdException {
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

//	@Override
//	public List<Skills> saveSkills(String email, List<Skills> skillsList) throws InvalidIdException {
//		User user = userRepository.findById(email).orElseThrow(()-> new InvalidIdException("No email Found..!!!"+email));
//		for(Skills skills:skillsList) {
//			skills.setUser(user);
//			user.getSkills().add(skills);
//			userRepository.save(user);
//		}
//		return skillsRepository.sa;
//	}

//	@Override
//	public User saveSkills(String userEmail, Skills skills) {
//	    // Fetch the user by email
//	    User user = userRepository.findByEmail(userEmail);
//	    if (user == null) {
//	        // Handle user not found error
//	        throw new RuntimeException("User not found with email: " + userEmail);
//	    }
//	    // Set the user for the skills
//	    skills.setUser(user);
//	    // Add the skills to the user's skills list
//	    user.getSkills().add(skills);
//	    // Save the user (this will also cascade the save operation to the skills)
//	    userRepository.save(user);
//	    // Return the saved skills
//	    return user;
//	}


}
