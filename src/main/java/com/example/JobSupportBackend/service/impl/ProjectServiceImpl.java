package com.example.JobSupportBackend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JobSupportBackend.entity.PostProject;
import com.example.JobSupportBackend.repo.ProjectRepo;
import com.example.JobSupportBackend.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepo postProjectRepository;

	@Override
	public PostProject save(PostProject project) {
		return postProjectRepository.save(project);
	}

	@Override
	public List<PostProject> getAllProjects() {
		return postProjectRepository.findAll();
	}

	 @Override
	    public Optional<PostProject> findById(Long id) {
	        return postProjectRepository.findById(id);
	    }
	
	
	
	
	
	
	
	
	
	
}
