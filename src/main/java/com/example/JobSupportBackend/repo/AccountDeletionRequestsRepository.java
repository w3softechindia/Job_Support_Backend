package com.example.JobSupportBackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.JobSupportBackend.entity.AccountDeletionRequests;

@Repository
public interface AccountDeletionRequestsRepository extends JpaRepository<AccountDeletionRequests, String>{

	void deleteById(String email);
	
}
