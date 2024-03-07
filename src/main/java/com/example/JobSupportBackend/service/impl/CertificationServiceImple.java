package com.example.JobSupportBackend.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JobSupportBackend.entity.Certification;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.exceptions.InvalidIdException;
import com.example.JobSupportBackend.repo.CertificationRepository;
import com.example.JobSupportBackend.repo.UserRepository;
import com.example.JobSupportBackend.service.CertificationService;

@Service
public class CertificationServiceImple implements CertificationService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CertificationRepository certificationRepository;

	@Override
	public void addCertications(String userEmail, List<Certification> certifications) throws InvalidIdException {
		User user = userRepository.findByEmail(userEmail);
		if(user!=null) {
			certifications.forEach(certificate->{
				certificate.setUser(user);
				certificationRepository.save(certificate);
			});
		}else {
			throw new InvalidIdException("Email not found..!!"+userEmail);
		}
		
	}

}
