package com.example.JobSupportBackend.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.JobSupportBackend.entity.AdminApprovedProposal;

@Repository
public interface AdminApprovedProposalRepository extends JpaRepository<AdminApprovedProposal, Integer> {
	
	Optional<AdminApprovedProposal> findByFreelancerEmail(String email);

}

