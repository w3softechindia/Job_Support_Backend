package com.example.JobSupportBackend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.JobSupportBackend.entity.PostProject;

@Repository
public interface ProjectRepo extends JpaRepository<PostProject, Long> {

	List<PostProject> findByUserEmail(String userEmail);

	@Query("SELECT p.id FROM PostProject p WHERE p.status = 'false'")
	List<Long> findIdsByStatusFalse();

}
