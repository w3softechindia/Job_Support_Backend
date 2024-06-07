package com.example.JobSupportBackend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JobSupportBackend.entity.Review;
import com.example.JobSupportBackend.repo.ReviewRepository;
import com.example.JobSupportBackend.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsByEmail(String email) {
        // Assuming reviewRepository.findByEmail returns a list of reviews
        return reviewRepository.findByEmail(email);
    }
}