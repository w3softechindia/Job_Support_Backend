package com.example.JobSupportBackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.JobSupportBackend.entity.Certification;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long>{
	
}
