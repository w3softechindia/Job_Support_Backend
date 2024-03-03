package com.example.JobSupportBackend.service;

import com.example.JobSupportBackend.entity.Admin;
import com.example.JobSupportBackend.exceptions.InvalidIdException;

public interface AdminService {
	
	public Admin register(Admin admin) throws InvalidIdException;
	
	public Admin login(String email,String password) throws InvalidIdException;
}
