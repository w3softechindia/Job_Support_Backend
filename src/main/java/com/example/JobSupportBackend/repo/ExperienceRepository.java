package com.example.JobSupportBackend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.JobSupportBackend.entity.Experience;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long>{

	List<Experience> findByUserEmail(String email);

}
