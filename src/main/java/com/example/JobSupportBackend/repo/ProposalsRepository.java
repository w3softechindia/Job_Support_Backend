package com.example.JobSupportBackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.JobSupportBackend.entity.SendProposal;

@Repository
public interface ProposalsRepository extends JpaRepository<SendProposal, Integer>{

}
