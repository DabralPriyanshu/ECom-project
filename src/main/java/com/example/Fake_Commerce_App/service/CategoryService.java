package com.example.Fake_Commerce_App.service;

import com.example.Fake_Commerce_App.dtos.CreateCategoryDto;
import com.example.Fake_Commerce_App.exceptions.ResourceNotFoundException;
import com.example.Fake_Commerce_App.repository.CategoryRepository;
import com.example.Fake_Commerce_App.schema.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category createCategory(CreateCategoryDto requestDto) {
        Category category = Category.builder()
                .name(requestDto.getName())
                .build();
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No category found with ID " + id)
        );
    }

    public void deleteCategory(Long id) {
       Category category= categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No category found with ID " + id));
                categoryRepository.deleteById(id);
    }
}

