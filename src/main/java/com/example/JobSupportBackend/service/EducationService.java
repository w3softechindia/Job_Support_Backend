package com.example.JobSupportBackend.service;

import java.util.List;

import com.example.JobSupportBackend.entity.Education;
import com.example.JobSupportBackend.exceptions.InvalidIdException;

public interface EducationService {

	void addEducations(String userEmail, List<Education> educations) throws InvalidIdException;

}
