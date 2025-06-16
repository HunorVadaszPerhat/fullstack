package com.example.backend.mapper.production.productcosthistory;

import com.example.backend.domain.model.production.product.Product;
import com.example.backend.repository.production.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductCostHistoryResolver {

    private final ProductRepository productRepository;

    public Product resolveProduct(Integer productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));
    }
}

