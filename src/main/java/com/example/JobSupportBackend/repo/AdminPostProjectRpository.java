package com.example.JobSupportBackend.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.JobSupportBackend.entity.AdminPostProject;

@Repository
public interface AdminPostProjectRpository extends JpaRepository<AdminPostProject, Long> {

//	@Query("SELECT p FROM AdminPostProject p LEFT JOIN FETCH p.sendProposal WHERE p.id = :projectId")
//	AdminPostProject findByIdWithProposals(@Param("projectId") Long projectId);

	@Query("SELECT a FROM AdminPostProject a WHERE a.project_id = :projectId")
	Optional<AdminPostProject> findByProject_id(@Param("projectId") Long projectId);
}
