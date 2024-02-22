package com.example.JobSupportBackend.service;

import java.util.Set;

import com.example.JobSupportBackend.entity.Skills;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.exceptions.InvalidIdException;

public interface SkillsService {
	
//	public List<Skills> saveSkills(String email,List<Skills> skillsList) throws InvalidIdException;
	
	User getUserByEmail(String email);
	
	void addSkills(String userEmail, Set<Skills> skills) throws InvalidIdException;
}
