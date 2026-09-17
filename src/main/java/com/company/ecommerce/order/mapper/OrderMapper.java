package com.company.ecommerce.order.mapper;

import com.company.ecommerce.order.dto.OrderItemResponseDTO;
import com.company.ecommerce.order.dto.OrderResponseDTO;
import com.company.ecommerce.order.entity.Order;
import com.company.ecommerce.order.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderItemResponseDTO toItemResponse(OrderItem item) {
        OrderItemResponseDTO response = new OrderItemResponseDTO();
        response.setId(item.getId());
        response.setProductId(item.getProductId());
        response.setProductName(item.getProductName());
        response.setSku(item.getSku());
        response.setUnitPrice(item.getUnitPrice());
        response.setQuantity(item.getQuantity());
        response.setLineTotal(item.getLineTotal());
        return response;
    }

    public OrderResponseDTO toResponse(Order order) {
        OrderResponseDTO response = new OrderResponseDTO();
        response.setId(order.getId());
        response.setOrderNumber(order.getOrderNumber());
        response.setCustomerId(order.getCustomerId());
        response.setStatus(order.getStatus());
        response.setTotalAmount(order.getTotalAmount());
        response.setCreatedAt(order.getCreatedAt());
        response.setUpdatedAt(order.getUpdatedAt());

        if (order.getItems() != null) {
            response.setItems(
                    order.getItems()
                            .stream()
                            .map(this::toItemResponse)
                            .collect(Collectors.toList())
            );
        }

        return response;
    }
}