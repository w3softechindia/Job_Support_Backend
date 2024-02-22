package com.example.JobSupportBackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.JobSupportBackend.entity.User;

@Repository
public interface UserRepository  extends JpaRepository<User, String>{

	User findByEmail(String email);
}
