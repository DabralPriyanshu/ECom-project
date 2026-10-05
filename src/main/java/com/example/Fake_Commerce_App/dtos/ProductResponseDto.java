package com.example.Fake_Commerce_App.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProductResponseDto {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String image;
    private BigDecimal rating;

}
