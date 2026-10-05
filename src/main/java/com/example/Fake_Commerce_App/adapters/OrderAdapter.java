package com.example.Fake_Commerce_App.adapters;

import com.example.Fake_Commerce_App.dtos.OrderItemResponseDto;
import com.example.Fake_Commerce_App.dtos.OrderResponseDto;
import com.example.Fake_Commerce_App.repository.OrderProductsRepository;
import com.example.Fake_Commerce_App.schema.Order;
import com.example.Fake_Commerce_App.schema.OrderProducts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Component
public class OrderAdapter {

    private final OrderProductsRepository orderProductsRepository;

    public OrderResponseDto mapToOrderDto(Order order) {

        List<OrderProducts> orderProducts = orderProductsRepository.findByOrderId(order.getId());
        return OrderResponseDto.builder()
                .id(order.getId())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .items(orderProducts.stream().map(p -> mapToOrderItemDto(p)).toList())
                .build();
    }

    public OrderItemResponseDto mapToOrderItemDto(OrderProducts orderProducts) {
        return OrderItemResponseDto.builder()
                .productId(orderProducts.getProduct().getId())
                .productImage(orderProducts.getProduct().getImage())
                .productName(orderProducts.getProduct().getTitle())
                .quantity(orderProducts.getQuantity())
                .productPrice(orderProducts.getProduct().getPrice())
                .subTotal(orderProducts.getProduct().getPrice().
                        multiply(BigDecimal.valueOf(orderProducts.getQuantity())))
                .build();
    }

}
