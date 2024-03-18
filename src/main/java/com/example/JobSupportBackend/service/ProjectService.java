package com.example.JobSupportBackend.service;

import java.util.List;
import java.util.Optional;

import com.example.JobSupportBackend.dto.ProjectDTO;
import com.example.JobSupportBackend.entity.PostProject;

public interface ProjectService {

	PostProject save(PostProject project);
	

	Optional<PostProject> findById(Long id);

	
	
	 PostProject saveProject(PostProject project, String userEmail);
	 
	 
	 List<PostProject> getProjectsByUserEmail(String userEmail);

	
	  List<ProjectDTO> getAllProjects();



	List<PostProject> findAll();

}
