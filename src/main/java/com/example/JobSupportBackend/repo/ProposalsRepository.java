package com.example.JobSupportBackend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.JobSupportBackend.entity.SendProposal;

@Repository
public interface ProposalsRepository extends JpaRepository<SendProposal, Integer> {

	List<SendProposal> findByUserEmail(String email);

	List<SendProposal> findByAdminPostProjectId(Long projectId);

	long countByUserEmail(String email);

    long countByProposalStatusAndUserEmail(String status, String email);
}
