package com.example.Fake_Commerce_App.repository;

import com.example.Fake_Commerce_App.schema.Order;
import com.example.Fake_Commerce_App.schema.OrderProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface OrderRepository extends JpaRepository<Order, Long> {

}