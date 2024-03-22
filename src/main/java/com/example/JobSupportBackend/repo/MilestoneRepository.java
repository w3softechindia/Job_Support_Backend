package com.example.JobSupportBackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.JobSupportBackend.entity.Milestone;

import jakarta.transaction.Transactional;

@Repository
public interface MilestoneRepository extends JpaRepository<Milestone, Integer> {

	@Modifying
	@Transactional
	@Query("DELETE FROM Milestone m WHERE m.sendProposal.proposalId = :proposalId")
	void deleteByProposalId(@Param("proposalId") int proposalId);

}
