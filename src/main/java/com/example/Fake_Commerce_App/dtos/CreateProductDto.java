package com.example.Fake_Commerce_App.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CreateProductDto {
    private String title;
    private String description;
    private BigDecimal price;
    private String image;
    private Long categoryId;
    private BigDecimal rating;
}
