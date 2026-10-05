package com.example.Fake_Commerce_App.repository;

import com.example.Fake_Commerce_App.schema.Order;
import com.example.Fake_Commerce_App.schema.OrderProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductsRepository extends JpaRepository<OrderProducts, Long> {
    List<OrderProducts> findByOrderId(Long id);

    @Query("SELECT op FROM OrderProducts op JOIN FETCH op.product WHERE op.order=:order")
    List<OrderProducts> findByOrderWithProduct(Order order);

}