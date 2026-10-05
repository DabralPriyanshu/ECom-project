package com.example.Fake_Commerce_App.service;

import com.example.Fake_Commerce_App.adapters.OrderAdapter;
import com.example.Fake_Commerce_App.dtos.*;
import com.example.Fake_Commerce_App.exceptions.ResourceNotFoundException;
import com.example.Fake_Commerce_App.repository.OrderProductsRepository;
import com.example.Fake_Commerce_App.repository.OrderRepository;
import com.example.Fake_Commerce_App.repository.ProductRepository;
import com.example.Fake_Commerce_App.schema.Order;
import com.example.Fake_Commerce_App.schema.OrderProducts;
import com.example.Fake_Commerce_App.schema.OrderStatus;
import com.example.Fake_Commerce_App.schema.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductsRepository orderProductsRepository;
    private final ProductRepository productRepository;
    private final OrderAdapter orderAdapter;


    public List<OrderResponseDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(orderAdapter::mapToOrderDto).toList();
    }


    public OrderResponseDto getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Order with ID " + id + " not found "));
        return orderAdapter.mapToOrderDto(order);
    }

    public void deleteOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Order with ID " + id + " not found "));

        orderRepository.deleteById(id);

    }

    @Transactional
    public OrderResponseDto createOrder(CreateOrderRequestDto createOrderRequestDto) {
        Order order = Order.builder()
                .status(OrderStatus.PENDING)
                .build();

        orderRepository.save(order);
        if (createOrderRequestDto.getOrderItems() != null) {

            /**
             * Problematic code N+1 query
             */

//            for (OrderItemRequestDto orderItemRequestDto : createOrderRequestDto.getOrderItems()) {
//                Product product = productRepository.findById(orderItemRequestDto.getProductId())
//                        .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID :" +
//                                orderItemRequestDto.getProductId()));
//                OrderProducts orderProducts = OrderProducts
//                        .builder()
//                        .order(order)
//                        .product(product)
//                        .quantity(orderItemRequestDto.getQuantity() != null ? orderItemRequestDto.getQuantity() : 1)
//                        .build();
//
//
//                orderProductsRepository.save(orderProducts);
            List<Long> productIds = createOrderRequestDto.getOrderItems()
                    .stream()
                    .map(items -> items.getProductId())
                    .toList();
            // if product is not found for specific id it will not return product for that
            // but,it does not return the list in order
            List<Product> productList = productRepository.findAllById(productIds);
            Map<Long, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getId, Function.identity()));
            for (Long id : productIds) {
                if (!productMap.containsKey(id)) {
                    log.error("Product with ID {} not found ",id);
                    throw new ResourceNotFoundException("Product not found with ID: " + id);
                }
            }

            List<OrderProducts> orderProducts = new ArrayList<>();
            for (OrderItemRequestDto orderItemRequestDto : createOrderRequestDto.getOrderItems()) {
                Product product = productMap.get(orderItemRequestDto.getProductId());
                OrderProducts orderProduct = OrderProducts
                        .builder()
                        .order(order)
                        .product(product)
                        .quantity(orderItemRequestDto.getQuantity() != null ? orderItemRequestDto.getQuantity() : 1)
                        .build();
                orderProducts.add(orderProduct);
                //bulk insert
                orderProductsRepository.saveAll(orderProducts);
            }


        }
        return orderAdapter.mapToOrderDto(order);
    }


    public OrderResponseDto updateOrder(Long id, UpdateOrderRequestDto updateOrderRequestDto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + id));

        if (updateOrderRequestDto.getStatus() != null) {
            order.setStatus(updateOrderRequestDto.getStatus());
            orderRepository.save(order);
        }
        if (updateOrderRequestDto.getOrderItems() != null) {
            List<Long> productIds = updateOrderRequestDto.getOrderItems().stream()
                    .map(item -> item.getProductId())
                    .toList();
            List<Product> productList = productRepository.findAllById(productIds);
            Map<Long, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getId, Function.identity()));
            for (Long pid : productIds) {
                if (!productMap.containsKey(pid)) {
                    throw new ResourceNotFoundException("Product not found with ID: " + pid);
                }
            }


            List<OrderProducts> toSave = new ArrayList<>();
            List<OrderProducts> toDelete = new ArrayList<>();

            Map<Long, OrderProducts> existingItems = orderProductsRepository.findByOrderWithProduct(order).stream()
                    .collect(Collectors.toMap(op -> op.getProduct().getId(), Function.identity()));


            for (OrderItemActionDto itemAction : updateOrderRequestDto.getOrderItems()) {
                Product product = productMap.get(itemAction.getProductId());
                OrderProducts existing = existingItems.get(product.getId());
                switch (itemAction.getAction()) {
                    case ADD -> {
                        if (existing != null) {
                            int addQty = itemAction.getQuantity() != null ? itemAction.getQuantity() : 1;
                            existing.setQuantity(existing.getQuantity() + addQty);
                            toSave.add(existing);
                        } else {
                            OrderProducts newItem = OrderProducts
                                    .builder()
                                    .order(order)
                                    .product(product)
                                    .quantity(itemAction.getQuantity() != null ? itemAction.getQuantity() : 1)
                                    .build();
                            existingItems.put(product.getId(), newItem);
                        }
                    }
                    case REMOVE -> {
                        if (existing == null) {
                            throw new ResourceNotFoundException("Product not found with ID :" + product.getId());
                        }
                        toDelete.add(existing);
                        existingItems.remove(product.getId());
                    }
                    case INCREMENT -> {
                        if (existing == null) {
                            throw new ResourceNotFoundException("Product not found with ID :" + product.getId());
                        }
                        existing.setQuantity(existing.getQuantity() + 1);
                        toSave.add(existing);
                    }
                    case DECREMENT -> {
                        if (existing == null) {
                            throw new ResourceNotFoundException("Product not found with ID :" + product.getId());
                        }
                        if (existing.getQuantity() <= 1) {
                            toDelete.add(existing);
                            existingItems.remove(product.getId());
                        } else {
                            existing.setQuantity(existing.getQuantity() - 1);
                            toSave.add(existing);
                        }
                    }
                }
            }
            if (!toSave.isEmpty()) {
                orderProductsRepository.saveAll(toSave);
            }
            if (!toDelete.isEmpty()) {
                orderProductsRepository.deleteAll(toDelete);
            }

        }

        return orderAdapter.mapToOrderDto(order);
    }

    public GetOrderSummaryResponseDto getOrderSummary(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new ResourceNotFoundException("Order not found with ID: " + orderId)
        );
        List<OrderProducts> orderProducts = orderProductsRepository.findByOrderWithProduct(order);
        List<OrderItemResponseDto> items = orderProducts.stream().map(orderAdapter::mapToOrderItemDto).toList();

        int totalItems = orderProducts.stream().mapToInt(OrderProducts::getQuantity).sum();
        BigDecimal totalPrice = orderProducts.stream().map(op -> op.getProduct()
                .getPrice().multiply(BigDecimal.valueOf(op.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
        return GetOrderSummaryResponseDto
                .builder()
                .id(order.getId())
                .status(order.getStatus())
                .items(items)
                .totalPrice(totalPrice)
                .totalItem(totalItems)
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();

    }
}


