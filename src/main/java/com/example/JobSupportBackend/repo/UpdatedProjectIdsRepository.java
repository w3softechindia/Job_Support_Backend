package com.example.JobSupportBackend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.JobSupportBackend.entity.UpdatedProjectIds;

@Repository
public interface UpdatedProjectIdsRepository  extends JpaRepository<UpdatedProjectIds, Long>{

	
	@Query("SELECT projectId FROM UpdatedProjectIds")
	List<Long> getAllUpdatedProjectIds();

	List<UpdatedProjectIds> findAllByProjectId(Long projectId);

	



	
	


	

}
