package com.example.JobSupportBackend.service;

import java.util.Set;

import com.example.JobSupportBackend.entity.Education;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.exceptions.InvalidIdException;

public interface EducationService {

	User getUserByEmail(String email);

	void addEducations(String userEmail, Set<Education> educations) throws InvalidIdException;

}
