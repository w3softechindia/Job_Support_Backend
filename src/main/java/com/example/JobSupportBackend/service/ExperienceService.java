package com.example.JobSupportBackend.service;

import java.util.Set;

import com.example.JobSupportBackend.entity.Experience;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.exceptions.InvalidIdException;

public interface ExperienceService {

	User getUserByEmail(String email);

	void addExperience(String userEmail, Set<Experience> experiences) throws InvalidIdException;

}
