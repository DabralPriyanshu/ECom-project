package com.example.Fake_Commerce_App.service;

import com.example.Fake_Commerce_App.dtos.CreateCategoryDto;
import com.example.Fake_Commerce_App.exceptions.ResourceNotFoundException;
import com.example.Fake_Commerce_App.repository.CategoryRepository;
import com.example.Fake_Commerce_App.schema.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// helps to run this file independently  without loading application context a
@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    // mocking category repository as we do not want to connect with actual database
    private CategoryRepository categoryRepository;

    @InjectMocks
    // we need actual object of category service this annotation will create actual object and inject mocks into it
    private CategoryService categoryService;


    @Test
    void createCategory_savesAndReturnCategory() {
        //arrange
        CreateCategoryDto dto = CreateCategoryDto.builder().name("Test category").build();
        Category testCategory = Category
                .builder()
                .name("Test category")
                .build();
        testCategory.setId(1L);
        when(categoryRepository.save(any(Category.class))).thenReturn(testCategory);
        //act
        Category result = categoryService.createCategory(dto);
        //assert
        assertEquals("Test category", result.getName());
        assertEquals(1L, result.getId());


    }

    @Test
    void getCategoryById_whenFound_returnCategory() {
        Category testCategory = Category
                .builder()
                .name("Test category")
                .build();
        testCategory.setId(1L);
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(testCategory));
        //act
        Long id = 1L;
        Category result = categoryService.getCategoryById(id);
        //assert
        assertEquals("Test category", result.getName());
        assertEquals(1L, result.getId());

    }

    @Test
    void getCategoryById_whenNotFound_ThrowsResourceNotFoundException() {
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategoryById(2L));
    }


}
