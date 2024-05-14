package com.example.JobSupportBackend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.JobSupportBackend.entity.CompletedProjects;

@Repository
public interface CompletedProjectRepository extends JpaRepository<CompletedProjects, Long>{

	List<CompletedProjects> findByFreelancer(String email);

	int countByFreelancer(String email);

}
