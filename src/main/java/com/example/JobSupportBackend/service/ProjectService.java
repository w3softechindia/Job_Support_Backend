package com.example.JobSupportBackend.service;

import java.util.List;
import java.util.Optional;

import com.example.JobSupportBackend.entity.PostProject;
import com.example.JobSupportBackend.entity.ProjectFile;

public interface ProjectService {

	PostProject save(PostProject project);

	List<PostProject> getAllProjects();

	  Optional<PostProject> findById(Long id);
	
	
	
	
	
   
}
