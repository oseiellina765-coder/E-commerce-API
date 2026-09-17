package com.company.ecommerce.order.controller;

import com.company.ecommerce.order.dto.OrderResponseDTO;
import com.company.ecommerce.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/V3/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Create order from the current cart
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(
            @RequestHeader("X-Customer-Id") String customerId) {

        OrderResponseDTO order = orderService.createOrderFromCart(customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    // Get all orders of the current customer
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getMyOrders(
            @RequestHeader("X-Customer-Id") String customerId) {

        return ResponseEntity.ok(orderService.getOrdersByCustomer(customerId));
    }

    // Get a specific order
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    // Update order status (usually for admin)
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDTO> updateStatus(
            @PathVariable UUID orderId,
            @RequestParam String status) {

        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }
}