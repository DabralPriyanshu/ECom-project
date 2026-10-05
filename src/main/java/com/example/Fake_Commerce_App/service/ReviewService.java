package com.example.Fake_Commerce_App.service;

import com.example.Fake_Commerce_App.dtos.GetReviewResponseDto;
import com.example.Fake_Commerce_App.exceptions.ResourceNotFoundException;
import com.example.Fake_Commerce_App.repository.ReviewRepository;
import com.example.Fake_Commerce_App.schema.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public List<Review> getReviewsByOrderId(Long orderId) {
        return reviewRepository.findByOrderId(orderId);
    }

    public List<Review> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }


    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    public Review getReviewById(Long id) {
        return reviewRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Review not found with ID: " + id)
        );

    }

}
