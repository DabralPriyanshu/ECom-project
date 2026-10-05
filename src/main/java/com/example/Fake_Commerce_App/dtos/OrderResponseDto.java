package com.example.Fake_Commerce_App.dtos;

import com.example.Fake_Commerce_App.schema.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDto {
    private Long id;

    private OrderStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<OrderItemResponseDto> items = new ArrayList<>();

}
