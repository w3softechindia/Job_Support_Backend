package com.example.JobSupportBackend.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
	public List<Long> findFalseStatusIds() {
		return postProjectRepository.findIdsByStatusFalse();
	}

	
	
	
	@Override
	public void toggleStatus(Long projectId) {
		Optional<PostProject> optionalProject = postProjectRepository.findById(projectId);
		if (optionalProject.isPresent()) {
			PostProject project = optionalProject.get();
			// Toggle the status
			if ("true".equals(project.getStatus())) {
				project.setStatus("false");
			} else {
				project.setStatus("true");
			}
			postProjectRepository.save(project);
		} else {
			// Handle case when project with given ID is not found
			throw new IllegalArgumentException("Project with ID " + projectId + " not found");
		}
	}

	
	
	
	
	
	
	
	@Override
	public List<Long> getExpiredProjectIds() {
		List<Long> expiredProjectIds = new ArrayList<>();
		List<PostProject> projects = postProjectRepository.findAll();
		Date currentDate = new Date();

		for (PostProject project : projects) {
			if (project.getDeadline_date().before(currentDate)) {
				expiredProjectIds.add(project.getId());
			}
		}

		return expiredProjectIds;
	}

	@Override
	public List<PostProject> findByIds(List<Long> ids) {
		return postProjectRepository.findAllById(ids);
	}

	
	

	

	@Override
    public List<PostProject> updateWorkingStatusForMultiple(List<Long> ids, String status) {
        List<PostProject> projectsToUpdate = postProjectRepository.findAllById(ids);
        projectsToUpdate.forEach(project -> project.setWorkingstatus(status));
        return postProjectRepository.saveAll(projectsToUpdate);
    }

	
	
	 @Override
	    public List<Long> findProjectIdsByWorkingStatus(String workingStatus) {
	        return postProjectRepository.findIdsByWorkingStatus(workingStatus);
	    }
	
	
	
	
}
