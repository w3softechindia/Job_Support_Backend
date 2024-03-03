package com.example.JobSupportBackend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JobSupportBackend.entity.Admin;
import com.example.JobSupportBackend.exceptions.InvalidIdException;
import com.example.JobSupportBackend.repo.AdminRepository;
import com.example.JobSupportBackend.service.AdminService;

@Service
public class AdminServiceImple implements AdminService{

	@Autowired
	AdminRepository adminRepository;
	
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
}
