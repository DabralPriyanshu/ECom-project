package com.example.Fake_Commerce_App.controller;

import com.example.Fake_Commerce_App.dtos.CreateProductDto;
import com.example.Fake_Commerce_App.dtos.DetailedProductResponseDto;
import com.example.Fake_Commerce_App.dtos.ProductResponseDto;
import com.example.Fake_Commerce_App.schema.Product;
import com.example.Fake_Commerce_App.service.ProductService;
import com.example.Fake_Commerce_App.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getAllProducts() {
        ApiResponse<List<ProductResponseDto>> apiResponse = ApiResponse.success(
                productService.getAllProducts(),
                "Products fetched successfully"
        );
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody CreateProductDto requestDto) {
        ApiResponse<Product> apiResponse = ApiResponse.success(
                productService.createProduct(requestDto),
                "Product created successfully"
        );
        return ResponseEntity.status(201).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getById(@PathVariable Long id) {
        ApiResponse<ProductResponseDto> apiResponse = ApiResponse.success(
                productService.getById(id),
                "Product fetched successfully"
        );
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        ApiResponse<Void> apiResponse = ApiResponse.success(
                null,
                "Product deleted successfully"
        );
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<ApiResponse<DetailedProductResponseDto>> getProductWithCategory(@PathVariable Long id) {
        ApiResponse<DetailedProductResponseDto> apiResponse = ApiResponse.success(
                productService.getProductWithCategory(id),
                "Product details fetched successfully"
        );
        return ResponseEntity.ok(apiResponse);
    }
}