package com.example.JobSupportBackend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JobSupportBackend.dto.ProjectDTO;
import com.example.JobSupportBackend.entity.PostProject;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.repo.ProjectRepo;
import com.example.JobSupportBackend.repo.UserRepository;
import com.example.JobSupportBackend.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepo postProjectRepository;

	@Autowired
	private UserRepository urp;

	@Override
	public PostProject save(PostProject project) {
		return postProjectRepository.save(project);
	}

	@Override
	public Optional<PostProject> findById(Long id) {
		return postProjectRepository.findById(id);
	}

	@Override
	public PostProject saveProject(PostProject project, String userEmail) {
		// Fetch the user by email
		User user = urp.findByEmail(userEmail);
		if (user == null) {
			// Handle case where user does not exist
			throw new IllegalArgumentException("User with email " + userEmail + " does not exist.");
		}

		// Set the user for the project
		project.setUser(user);

		// Your existing logic for parsing and setting attributes goes here...

		// Save the project
		return postProjectRepository.save(project);
	}

	@Override
	public List<PostProject> getProjectsByUserEmail(String userEmail) {
		return postProjectRepository.findByUserEmail(userEmail);
	}

	@Override
	public List<ProjectDTO> getAllProjects() {
		return null;
	}

	@Override
	public List<PostProject> findAll() {
		return postProjectRepository.findAll();
	}

	@Override
	public PostProject getProjectById(long id) {
		return postProjectRepository.findById(id).get();
	}

}
