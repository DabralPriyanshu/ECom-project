package com.example.Fake_Commerce_App.dtos;


import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DetailedProductResponseDto extends ProductResponseDto {
    private String category;
}
