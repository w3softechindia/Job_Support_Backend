package com.example.JobSupportBackend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.JobSupportBackend.entity.PostProject;

@Repository
public interface ProjectRepo extends JpaRepository<PostProject, Long> {

	List<PostProject> findByUsersEmail(String userEmail);

	@Query("SELECT p.id FROM PostProject p WHERE p.status = 'false'")
	List<Long> findIdsByStatusFalse();
	
	 @Query("SELECT p.id FROM PostProject p WHERE p.workingstatus = :workingStatus")
	    List<Long> findIdsByWorkingStatus(@Param("workingStatus") String workingStatus);

	
	
	 
}

