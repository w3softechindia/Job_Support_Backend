package com.example.JobSupportBackend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.JobSupportBackend.entity.Certification;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Integer>{

	List<Certification> findByUsersEmail(String email);

	void deleteByUsersEmail(String email);
	
}
