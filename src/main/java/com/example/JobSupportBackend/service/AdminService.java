package com.example.JobSupportBackend.service;

import com.example.JobSupportBackend.entity.Admin;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.exceptions.InvalidIdException;
import com.example.JobSupportBackend.exceptions.ResourceNotFoundException;

public interface AdminService {
	
	public Admin register(Admin admin) throws InvalidIdException;
	
	public Admin login(String email,String password) throws InvalidIdException;
	
	public User setStatus(String email,String status) throws ResourceNotFoundException;
	
	public String deleteUser(String email) throws InvalidIdException;
}
