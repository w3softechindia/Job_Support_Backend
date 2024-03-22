package com.example.JobSupportBackend.service;

import java.util.List;

import com.example.JobSupportBackend.entity.AdminPostProject;

import com.example.JobSupportBackend.entity.PostProject;

public interface AdminProjectService {
	
	AdminPostProject getProjectById(Long id);
	
	List<AdminPostProject> getAllProjects();
}
