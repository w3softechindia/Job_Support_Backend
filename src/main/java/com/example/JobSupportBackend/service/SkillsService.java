package com.example.JobSupportBackend.service;

import java.util.List;

import com.example.JobSupportBackend.entity.Skills;
import com.example.JobSupportBackend.exceptions.InvalidIdException;

public interface SkillsService {
	
//	public List<Skills> saveSkills(String email,List<Skills> skillsList) throws InvalidIdException;
	
	void addSkills(String userEmail, List<Skills> skills) throws InvalidIdException;
}
