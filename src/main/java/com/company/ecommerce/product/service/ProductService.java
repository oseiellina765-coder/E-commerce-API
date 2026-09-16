package com.company.ecommerce.product.service;

import com.company.ecommerce.product.entity.Product;
import com.company.ecommerce.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // ==================== CREATE ====================
    public Product createProduct(Product product) {
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    // ==================== READ ALL ====================
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // ==================== READ ONE ====================
    public Product getProductById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    // ==================== UPDATE ====================
    public Product updateProduct(UUID id, Product productDetails) {
        Product existingProduct = getProductById(id);

        existingProduct.setName(productDetails.getName());
        existingProduct.setDescription(productDetails.getDescription());
        existingProduct.setSku(productDetails.getSku());
        existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setStockQuantity(productDetails.getStockQuantity());
        existingProduct.setImageUrl(productDetails.getImageUrl());
        existingProduct.setActive(productDetails.getActive());
        existingProduct.setUpdatedAt(LocalDateTime.now());

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(UUID id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
}