package com.example.JobSupportBackend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.JobSupportBackend.entity.AdminPostProject;
import com.example.JobSupportBackend.entity.PostProject;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.repo.AdminPostProjectRpository;
import com.example.JobSupportBackend.repo.UserRepository;
import com.example.JobSupportBackend.service.AdminProjectService;

public class AdminProjectServiceImpl  implements AdminProjectService{
	
	
	@Autowired
	private AdminPostProjectRpository adminPostProjectRpository;
	

	@Autowired
	private UserRepository urp;
	
	
	
	
}
