package com.example.JobSupportBackend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JobSupportBackend.entity.UpdatedProjectIds;
import com.example.JobSupportBackend.repo.UpdatedProjectIdsRepository;
import com.example.JobSupportBackend.service.UpdatedProjectIdsService;

@Service
public class UpdatedProjectIdsServiceImpl implements UpdatedProjectIdsService {

	@Autowired
	private UpdatedProjectIdsRepository idsRepository;

	@Override
	public void saveUpdatedProjectIds(List<Long> projectIds) {
		for (Long projectId : projectIds) {
			UpdatedProjectIds updatedProject = new UpdatedProjectIds();
			updatedProject.setProjectId(projectId);
			idsRepository.save(updatedProject);

		}
	}

	 @Override
	    public List<Long> getAllUpdatedProjectIds() {
	        return idsRepository.getAllUpdatedProjectIds();
	    }

	 
	 @Override
	    public void deleteProjectById(Long projectId) {
	        List<UpdatedProjectIds> projects = idsRepository.findAllByProjectId(projectId);
	        if (!projects.isEmpty()) {
	        	idsRepository.deleteAll(projects);
	        } else {
	            throw new IllegalArgumentException("Project with ID " + projectId + " not found.");
	        }
	    }
	 
	 
	 
}
