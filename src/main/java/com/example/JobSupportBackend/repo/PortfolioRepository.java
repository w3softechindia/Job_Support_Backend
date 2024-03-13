package com.example.JobSupportBackend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.JobSupportBackend.entity.Portfolio;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Integer>{

	List<Portfolio> findByUserEmail(String email);
	
	Portfolio findByUserEmailAndTitle(String email, String title);
}
