package com.company.ecommerce.cart.mapper;

import com.company.ecommerce.cart.dto.CartItemResponseDTO;
import com.company.ecommerce.cart.dto.CartResponseDTO;
import com.company.ecommerce.cart.entity.Cart;
import com.company.ecommerce.cart.entity.CartItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CartMapper {

    public CartItemResponseDTO toItemResponse(CartItem item) {
        CartItemResponseDTO response = new CartItemResponseDTO();
        response.setId(item.getId());
        response.setProductId(item.getProductId());
        response.setQuantity(item.getQuantity());
        response.setCreatedAt(item.getCreatedAt());
        response.setUpdatedAt(item.getUpdatedAt());
        return response;
    }

    public CartResponseDTO toResponse(Cart cart) {
        CartResponseDTO response = new CartResponseDTO();
        response.setId(cart.getId());
        response.setCustomerId(cart.getCustomerId());
        response.setCreatedAt(cart.getCreatedAt());
        response.setUpdatedAt(cart.getUpdatedAt());

        if (cart.getItems() != null) {
            response.setItems(
                    cart.getItems()
                            .stream()
                            .map(this::toItemResponse)
                            .collect(Collectors.toList())
            );
        }

        return response;
    }
}