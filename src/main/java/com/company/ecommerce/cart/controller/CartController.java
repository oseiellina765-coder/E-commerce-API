package com.company.ecommerce.cart.controller;

import com.company.ecommerce.cart.dto.CartItemRequestDTO;
import com.company.ecommerce.cart.dto.CartResponseDTO;
import com.company.ecommerce.cart.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/V2/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Get current customer's cart
    @GetMapping
    public ResponseEntity<CartResponseDTO> getCart(@RequestHeader("X-Customer-Id") String customerId) {
        return ResponseEntity.ok(cartService.getOrCreateCart(customerId));
    }

    // Add item to cart
    @PostMapping("/items")
    public ResponseEntity<CartResponseDTO> addItem(
            @RequestHeader("X-Customer-Id") String customerId,
            @Valid @RequestBody CartItemRequestDTO request) {

        return ResponseEntity.ok(cartService.addItemToCart(customerId, request));
    }

    // Update item quantity
    @PutMapping("/items/{productId}")
    public ResponseEntity<CartResponseDTO> updateItem(
            @RequestHeader("X-Customer-Id") String customerId,
            @PathVariable UUID productId,
            @RequestParam Integer quantity) {

        return ResponseEntity.ok(cartService.updateItemQuantity(customerId, productId, quantity));
    }

    // Remove item from cart
    @DeleteMapping("/items/{productId}")
    public ResponseEntity<CartResponseDTO> removeItem(
            @RequestHeader("X-Customer-Id") String customerId,
            @PathVariable UUID productId) {

        return ResponseEntity.ok(cartService.removeItemFromCart(customerId, productId));
    }

    // Clear the whole cart
    @DeleteMapping
    public ResponseEntity<Void> clearCart(@RequestHeader("X-Customer-Id") String customerId) {
        cartService.clearCart(customerId);
        return ResponseEntity.noContent().build();
    }
}