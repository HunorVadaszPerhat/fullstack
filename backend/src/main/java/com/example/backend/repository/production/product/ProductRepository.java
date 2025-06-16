package com.example.backend.repository.production.product;

import com.example.backend.domain.model.production.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}

