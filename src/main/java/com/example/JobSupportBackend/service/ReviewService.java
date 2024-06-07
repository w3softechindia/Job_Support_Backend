package com.example.JobSupportBackend.service;

import java.util.List;

import com.example.JobSupportBackend.entity.Review;

public interface ReviewService {
    Review saveReview(Review review);
    
    List<Review> getReviewsByEmail(String email);

}