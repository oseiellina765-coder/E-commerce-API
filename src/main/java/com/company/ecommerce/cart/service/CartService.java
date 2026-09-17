package com.company.ecommerce.cart.service;

import com.company.ecommerce.cart.dto.CartItemRequestDTO;
import com.company.ecommerce.cart.dto.CartResponseDTO;
import com.company.ecommerce.cart.entity.Cart;
import com.company.ecommerce.cart.entity.CartItem;
import com.company.ecommerce.cart.mapper.CartMapper;
import com.company.ecommerce.cart.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    public CartService(CartRepository cartRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
    }

    // Get or create cart for a customer
    @Transactional
    public CartResponseDTO getOrCreateCart(String customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setCustomerId(customerId);
                    newCart.setCreatedAt(LocalDateTime.now());
                    newCart.setUpdatedAt(LocalDateTime.now());
                    return cartRepository.save(newCart);
                });

        return cartMapper.toResponse(cart);
    }

    // Add item to cart (or increase quantity if product already exists)
    @Transactional
    public CartResponseDTO addItemToCart(String customerId, CartItemRequestDTO request) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setCustomerId(customerId);
                    newCart.setCreatedAt(LocalDateTime.now());
                    newCart.setUpdatedAt(LocalDateTime.now());
                    return cartRepository.save(newCart);
                });

        // Check if the product already exists in the cart
        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(request.getProductId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            // Product already in cart → increase quantity
            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
            existingItem.setUpdatedAt(LocalDateTime.now());
        } else {
            // New product → create new CartItem
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProductId(request.getProductId());
            newItem.setQuantity(request.getQuantity());
            newItem.setCreatedAt(LocalDateTime.now());
            newItem.setUpdatedAt(LocalDateTime.now());

            cart.getItems().add(newItem);
        }

        cart.setUpdatedAt(LocalDateTime.now());
        Cart savedCart = cartRepository.save(cart);

        return cartMapper.toResponse(savedCart);
    }

    // Update quantity of a specific item
    @Transactional
    public CartResponseDTO updateItemQuantity(String customerId, UUID productId, Integer quantity) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        if (quantity <= 0) {
            cart.getItems().remove(item); // remove if quantity is 0 or less
        } else {
            item.setQuantity(quantity);
            item.setUpdatedAt(LocalDateTime.now());
        }

        cart.setUpdatedAt(LocalDateTime.now());
        Cart savedCart = cartRepository.save(cart);

        return cartMapper.toResponse(savedCart);
    }

    // Remove a product from the cart
    @Transactional
    public CartResponseDTO removeItemFromCart(String customerId, UUID productId) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getItems().removeIf(item -> item.getProductId().equals(productId));
        cart.setUpdatedAt(LocalDateTime.now());

        Cart savedCart = cartRepository.save(cart);
        return cartMapper.toResponse(savedCart);
    }

    // Clear the entire cart
    @Transactional
    public void clearCart(String customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getItems().clear();
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
    }
}