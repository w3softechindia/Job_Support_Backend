package com.example.JobSupportBackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.JobSupportBackend.entity.Milestone;

@Repository
public interface MilestoneRepository extends JpaRepository<Milestone, Integer>{

}