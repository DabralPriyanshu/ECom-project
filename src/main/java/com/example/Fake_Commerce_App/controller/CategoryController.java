package com.example.Fake_Commerce_App.controller;

import com.example.Fake_Commerce_App.dtos.CreateCategoryDto;
import com.example.Fake_Commerce_App.schema.Category;
import com.example.Fake_Commerce_App.service.CategoryService;
import com.example.Fake_Commerce_App.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<Category>> createCategory(@RequestBody CreateCategoryDto requestDto) {
        ApiResponse<Category> apiResponse = ApiResponse.success(
                categoryService.createCategory(requestDto),
                "Category created successfully"
        );
        return ResponseEntity.status(201).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {
        ApiResponse<List<Category>> apiResponse = ApiResponse.success(
                categoryService.getAllCategories(),
                "Categories fetched successfully"
        );
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> getCategory(@PathVariable Long id) {
        ApiResponse<Category> apiResponse = ApiResponse.success(
                categoryService.getCategoryById(id),
                "Category fetched successfully"
        );
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        ApiResponse<Void> apiResponse = ApiResponse.success(
                null,
                "Category deleted successfully"
        );
        return ResponseEntity.ok(apiResponse);
    }
}