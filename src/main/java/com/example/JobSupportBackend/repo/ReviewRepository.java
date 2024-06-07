package com.example.JobSupportBackend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.JobSupportBackend.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

	@Query("SELECT r FROM Review r WHERE r.email = :email")
	List<Review> findByEmail(String email);

	List<Review> findByFreelancer(String email);
}
