package com.example.JobSupportBackend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JobSupportBackend.entity.Admin;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.exceptions.InvalidIdException;
import com.example.JobSupportBackend.exceptions.ResourceNotFoundException;
import com.example.JobSupportBackend.repo.AdminRepository;
import com.example.JobSupportBackend.repo.CertificationRepository;
import com.example.JobSupportBackend.repo.DeletedAccountsRepository;
import com.example.JobSupportBackend.repo.EducationRepository;
import com.example.JobSupportBackend.repo.ExperienceRepository;
import com.example.JobSupportBackend.repo.LanguageRepository;
import com.example.JobSupportBackend.repo.PortfolioRepository;
import com.example.JobSupportBackend.repo.SkillsRepository;
import com.example.JobSupportBackend.repo.UserRepository;
import com.example.JobSupportBackend.service.AdminService;

@Service
public class AdminServiceImple implements AdminService{

	@Autowired
	AdminRepository adminRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private SkillsRepository skillsRepository;

	@Autowired
	private EducationRepository educationRepository;

	@Autowired
	private CertificationRepository certificationRepository;

	@Autowired
	private ExperienceRepository experienceRepository;

	@Autowired
	private LanguageRepository languageRepository;

	@Autowired
	private DeletedAccountsRepository accountsRepository;

	@Autowired
	private PortfolioRepository portfolioRepository;

	
	@Override
	public Admin register(Admin admin) throws InvalidIdException {
		return adminRepository.save(admin);
	}

	@Override
	public Admin login(String email, String password) throws InvalidIdException {
		Admin admin = adminRepository.findById(email).orElseThrow(()-> new InvalidIdException("Email Doesn't exist...!!!"+email));
		if(admin.getPassword().equals(password)) {
			return admin;
		}
		else {
			throw new InvalidIdException("Invalid Password...!!!");
		}
	}

	@Override
	public User setStatus(String email, String status) throws ResourceNotFoundException {
		User byEmail = userRepository.findByEmail(email);
		if(byEmail!=null) {
			byEmail.setStatus(status);
			return userRepository.save(byEmail);
		}
		else {
			throw new ResourceNotFoundException("Email Not Found..!!!"+email);
		}
	}

	@Override
	public String deleteUser(String email) throws InvalidIdException {
		User byEmail = userRepository.findByEmail(email);
		if(byEmail!=null) {
			skillsRepository.deleteByUserEmail(email);
			educationRepository.deleteByUserEmail(email);
			certificationRepository.deleteByUserEmail(email);
			experienceRepository.deleteByUserEmail(email);
			languageRepository.deleteByUserEmail(email);
			accountsRepository.deleteById(email);
			portfolioRepository.deleteByUserEmail(email);
			
			userRepository.delete(byEmail);	
			
			return "User Account Deleted Successfully..!!";
			}
		else {
			throw new InvalidIdException("Email not Found..!!!"+email);
		}
	}
}
