package com.example.JobSupportBackend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.JobSupportBackend.entity.Education;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long>{

	List<Education> findByUserEmail(String email);
}
