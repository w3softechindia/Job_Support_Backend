package com.example.JobSupportBackend.service;

import java.util.List;
import com.example.JobSupportBackend.entity.Certification;
import com.example.JobSupportBackend.exceptions.InvalidIdException;

public interface CertificationService {
	void addCertications(String userEmail, List<Certification> certifications) throws InvalidIdException;

}
