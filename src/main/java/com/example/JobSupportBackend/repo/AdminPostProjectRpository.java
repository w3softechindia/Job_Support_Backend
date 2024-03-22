package com.example.JobSupportBackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.JobSupportBackend.entity.AdminPostProject;

@Repository
public interface AdminPostProjectRpository extends JpaRepository<AdminPostProject, Long> {

//	@Query("SELECT p FROM AdminPostProject p LEFT JOIN FETCH p.sendProposal WHERE p.id = :projectId")
//	AdminPostProject findByIdWithProposals(@Param("projectId") Long projectId);
}
