package com.example.JobSupportBackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.JobSupportBackend.entity.Review;
import com.example.JobSupportBackend.service.ReviewService;

@RestController
public class ReviewController {

	@Autowired
	private ReviewService reviewService;

	@PostMapping("/add/reviews/{email}")
	public ResponseEntity<Review> createReview(@RequestBody Review review, @PathVariable String email) {
		review.setEmail(email); // Set the email from path variable
		Review savedReview = reviewService.saveReview(review);
		return ResponseEntity.ok(savedReview);
	}

	@GetMapping("/log/credits/{email}")
	public ResponseEntity<List<Review>> getReviewsByEmail(@PathVariable String email) {
		List<Review> reviews = reviewService.getReviewsByEmail(email);
		if (reviews.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(reviews);
	}

}
