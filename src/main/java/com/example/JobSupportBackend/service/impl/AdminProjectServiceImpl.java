package com.example.JobSupportBackend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JobSupportBackend.entity.AdminPostProject;
import com.example.JobSupportBackend.repo.AdminPostProjectRpository;
import com.example.JobSupportBackend.service.AdminProjectService;
@Service
public class AdminProjectServiceImpl  implements AdminProjectService{
	
	
	@Autowired
	private AdminPostProjectRpository adminPostProjectRpository;
	
	@Override
	public AdminPostProject getProjectById(Long id) {
		return adminPostProjectRpository.findById(id).get();
	}

	@Override
	public List<AdminPostProject> getAllProjects() {
		return adminPostProjectRpository.findAll();
	}
}
