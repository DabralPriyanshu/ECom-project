package com.example.Fake_Commerce_App.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetReviewResponseDto {

    private Long id;
    private Long productId;
    private Long orderId;
    private BigDecimal rating;
    private String comment;
    private LocalDateTime createdAt;
}
