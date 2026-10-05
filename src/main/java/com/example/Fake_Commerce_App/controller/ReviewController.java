package com.example.Fake_Commerce_App.controller;

import com.example.Fake_Commerce_App.schema.Review;
import com.example.Fake_Commerce_App.service.ReviewService;
import com.example.Fake_Commerce_App.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Review>> getReviewById(@PathVariable Long id) {
        ApiResponse<Review> apiResponse = ApiResponse.success(
                reviewService.getReviewById(id),
                "Review fetched successfully"
        );
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<List<Review>>> getReviewsByProductId(@PathVariable Long productId) {
        ApiResponse<List<Review>> apiResponse = ApiResponse.success(
                reviewService.getReviewsByProductId(productId),
                "Product reviews fetched successfully"
        );
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse<List<Review>>> getReviewsByOrderId(@PathVariable Long orderId) {
        ApiResponse<List<Review>> apiResponse = ApiResponse.success(
                reviewService.getReviewsByOrderId(orderId),
                "Order reviews fetched successfully"
        );
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Review>> createReview(@RequestBody Review review) {
        ApiResponse<Review> apiResponse = ApiResponse.success(
                reviewService.createReview(review),
                "Review created successfully"
        );
        return ResponseEntity.status(201).body(apiResponse);
    }
}