package com.example.Fake_Commerce_App.repository;

import com.example.Fake_Commerce_App.schema.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

//    @Query(nativeQuery = true, value = "SELECT p.*,c.name as category FROM products p INNER " +
//            "JOIN category c ON p.category_id=c.id Where p.id=:id")
      @Query("SELECT p FROM Product p JOIN FETCH p.category WHERE p.id=:id")
      List<Product> findProductWithDetailsById(Long id);
}
