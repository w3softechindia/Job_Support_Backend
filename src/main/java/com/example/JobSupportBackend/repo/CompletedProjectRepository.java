package com.example.JobSupportBackend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.JobSupportBackend.entity.CompletedProjects;

@Repository
public interface CompletedProjectRepository extends JpaRepository<CompletedProjects, Long> {

	List<CompletedProjects> findByFreelancer(String email);

	int countByFreelancer(String email);

	@Query("SELECT c FROM CompletedProjects c WHERE c.employer = :employerEmail")
	List<CompletedProjects> findByEmployerEmail(@Param("employerEmail") String employerEmail);

}
