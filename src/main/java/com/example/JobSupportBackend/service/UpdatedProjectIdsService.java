package com.example.JobSupportBackend.service;

import java.util.List;

public interface UpdatedProjectIdsService {

	void saveUpdatedProjectIds(List<Long> projectIds);

	  
	List<Long> getAllUpdatedProjectIds();
}