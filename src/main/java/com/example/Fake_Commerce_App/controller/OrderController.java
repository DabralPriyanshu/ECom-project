package com.example.Fake_Commerce_App.controller;

import com.example.Fake_Commerce_App.dtos.CreateOrderRequestDto;
import com.example.Fake_Commerce_App.dtos.GetOrderSummaryResponseDto;
import com.example.Fake_Commerce_App.dtos.OrderResponseDto;
import com.example.Fake_Commerce_App.dtos.UpdateOrderRequestDto;
import com.example.Fake_Commerce_App.service.OrderService;
import com.example.Fake_Commerce_App.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponseDto>>> getAllOrders() {
        ApiResponse<List<OrderResponseDto>> apiResponse = ApiResponse.success(
                orderService.getAllOrders(),
                "Orders fetched successfully"
        );
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> getOrderById(@PathVariable Long id) {
        ApiResponse<OrderResponseDto> apiResponse = ApiResponse.success(
                orderService.getOrderById(id),
                "Order fetched successfully"
        );
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOrderById(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        ApiResponse<Void> apiResponse = ApiResponse.success(
                null,
                "Order deleted successfully"
        );
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponseDto>> createOrder(@RequestBody CreateOrderRequestDto
                                                                             createOrderRequestDto) {
        ApiResponse<OrderResponseDto> apiResponse = ApiResponse.success(
                orderService.createOrder(createOrderRequestDto),
                "Order created successfully"
        );
        return ResponseEntity.status(201).body(apiResponse);
    }

    @PutMapping("/{id}")

    public ResponseEntity<ApiResponse<OrderResponseDto>> updateOrder(
            @PathVariable Long id, @RequestBody UpdateOrderRequestDto updateOrderRequestDto
    ) {
        ApiResponse<OrderResponseDto> apiResponse = ApiResponse.success(
                orderService.updateOrder(id, updateOrderRequestDto),
                "Order updated successfully"
        );
        return ResponseEntity.status(200).body(apiResponse);
    }


    @GetMapping("/{id}/summary")

    public ResponseEntity<ApiResponse<GetOrderSummaryResponseDto>> getOrderSummary(@PathVariable Long id) {
        ApiResponse<GetOrderSummaryResponseDto> apiResponse = ApiResponse.success(
                orderService.getOrderSummary(id),
                "Fetched order summary"
        );
        return ResponseEntity.status(200).body(apiResponse);
    }
}