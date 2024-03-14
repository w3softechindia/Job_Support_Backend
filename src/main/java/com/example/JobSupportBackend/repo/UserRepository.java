package com.example.JobSupportBackend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import com.example.JobSupportBackend.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	User findByEmail(String email);

	@Modifying
	@Query(value = "UPDATE User u SET u.imageBytes = :imageBytes WHERE u.email = :email")
	void saveImageBytes(String email, byte[] imageBytes);

	List<User> findByRole(String role);

	List<User> findByRoleAndStatus(String role, String status);

}
