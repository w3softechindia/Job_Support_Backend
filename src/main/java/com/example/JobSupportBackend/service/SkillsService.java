package com.example.JobSupportBackend.service;

import java.util.List;

import com.example.JobSupportBackend.entity.Skills;
import com.example.JobSupportBackend.exceptions.InvalidIdException;

public interface SkillsService {
		
	void addSkills(String userEmail, List<Skills> skills) throws InvalidIdException;
	
	Skills findByName(String skillName);
}
