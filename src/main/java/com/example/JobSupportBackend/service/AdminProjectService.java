package com.example.JobSupportBackend.service;

import java.util.List;

import com.example.JobSupportBackend.entity.AdminPostProject;

public interface AdminProjectService {
	
	AdminPostProject getProjectById(Long id);
	
	List<AdminPostProject> getAllProjects();
	
}
