package com.example.JobSupportBackend.service;

import java.util.Set;

import com.example.JobSupportBackend.entity.Certification;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.exceptions.InvalidIdException;

public interface CertificationService {

	User getUserByEmail(String email);

	void addCertications(String userEmail, Set<Certification> certifications) throws InvalidIdException;

}
