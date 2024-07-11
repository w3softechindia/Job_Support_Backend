package com.example.JobSupportBackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.JobSupportBackend.entity.DeletedAccounts;

@Repository
public interface DeletedAccountsRepository extends JpaRepository<DeletedAccounts, String>{

}
