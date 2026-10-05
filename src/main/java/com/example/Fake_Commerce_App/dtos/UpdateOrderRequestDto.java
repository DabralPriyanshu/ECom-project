package com.example.Fake_Commerce_App.dtos;

import com.example.Fake_Commerce_App.schema.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateOrderRequestDto {
    private OrderStatus status;
    private List<OrderItemActionDto> orderItems;
}
