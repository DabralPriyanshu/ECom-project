package com.example.Fake_Commerce_App.service;

import com.example.Fake_Commerce_App.dtos.CreateProductDto;
import com.example.Fake_Commerce_App.dtos.DetailedProductResponseDto;
import com.example.Fake_Commerce_App.dtos.ProductResponseDto;
import com.example.Fake_Commerce_App.exceptions.ResourceNotFoundException;
import com.example.Fake_Commerce_App.repository.CategoryRepository;
import com.example.Fake_Commerce_App.repository.ProductRepository;
import com.example.Fake_Commerce_App.schema.Category;
import com.example.Fake_Commerce_App.schema.Product;
import com.example.Fake_Commerce_App.service.cache.ProductRedisCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRedisCache redisCache;


    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream().map(p -> mapToDto(p)).toList();
    }

    public ProductResponseDto getById(Long id) {
        java.util.Optional<ProductResponseDto> productResponseDto = redisCache.getSummary(id);

        if (productResponseDto.isPresent()) {
            log.info("Cache hit for product summary :{} ",id);
            return productResponseDto.get();
        } else {
            log.info("Cache miss for product summary :{} ",id);
            Product product = productRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Product with ID " + id + " not found !!!"));
            ProductResponseDto responseDto = mapToDto(product);
            redisCache.putProductSummary(id, responseDto);
            return responseDto;
        }

    }

    public Product createProduct(CreateProductDto requestDto) {
        Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("No category found with ID " + requestDto.getCategoryId()));

        Product product = Product.builder()
                .title(requestDto.getTitle())
                .price(requestDto.getPrice())
                .image(requestDto.getImage())
                .category(category)
                .description(requestDto.getDescription())
                .rating(requestDto.getRating())
                .build();
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public DetailedProductResponseDto getProductWithCategory(Long id) {
        return mapToDetailedDto(productRepository.findProductWithDetailsById(id).get(0));

    }


    public ProductResponseDto mapToDto(Product product) {
        return ProductResponseDto.builder().
                title(product.getTitle())
                .price(product.getPrice())
                .id(product.getId())
                .rating(product.getRating())
                .image(product.getImage())
                .description(product.getDescription())
                .build();
    }

    public DetailedProductResponseDto mapToDetailedDto(Product product) {
        return DetailedProductResponseDto.builder().
                title(product.getTitle())
                .price(product.getPrice())
                .id(product.getId())
                .rating(product.getRating())
                .image(product.getImage())
                .description(product.getDescription())
                // getCategory with do again db query if we you native sql query
                // to resolve this we have to do hibernate query
                .category(product.getCategory().getName())
                .build();
    }
}
