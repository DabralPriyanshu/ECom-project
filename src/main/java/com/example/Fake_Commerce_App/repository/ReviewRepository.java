package com.example.Fake_Commerce_App.repository;

import com.example.Fake_Commerce_App.schema.Product;
import com.example.Fake_Commerce_App.schema.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByProductId(Long productId);

    List<Review> findByOrderId(Long orderId);
}