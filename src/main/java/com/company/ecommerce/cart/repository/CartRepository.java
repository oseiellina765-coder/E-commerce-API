package com.company.ecommerce.cart.repository;

import com.company.ecommerce.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {

    // Find cart by customer (Keycloak subject)
    Optional<Cart> findByCustomerId(String customerId);
}