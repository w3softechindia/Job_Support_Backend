package com.example.JobSupportBackend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.JobSupportBackend.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {

	Users findByEmail(String email);

	@Modifying
	@Query(value = "UPDATE Users u SET u.imageBytes = :imageBytes WHERE u.email = :email")
	void saveImageBytes(String email, byte[] imageBytes);

	List<Users> findByRole(String role);

	List<Users> findByRoleAndStatus(String role, String status);

	int countByRole(String role);

	int countByRoleAndStatus(String role, String status);

	boolean existsByEmail(String email);
}
