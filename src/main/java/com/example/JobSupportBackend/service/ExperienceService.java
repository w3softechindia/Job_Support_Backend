package com.example.JobSupportBackend.service;

import java.util.List;
import java.util.Set;

import com.example.JobSupportBackend.entity.Experience;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.exceptions.InvalidIdException;

public interface ExperienceService {
	void addExperience(String userEmail, List<Experience> experiences) throws InvalidIdException;

}
