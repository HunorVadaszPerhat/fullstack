package com.example.backend.mapper.production.productproductphoto;

import com.example.backend.domain.model.production.product.Product;
import com.example.backend.domain.model.production.productphoto.ProductPhoto;
import com.example.backend.repository.production.product.ProductRepository;
import com.example.backend.repository.production.productphoto.ProductPhotoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductProductPhotoResolver {

    private final ProductRepository productRepository;
    private final ProductPhotoRepository productPhotoRepository;

    public Product resolveProduct(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    public ProductPhoto resolveProductPhoto(Integer id) {
        return productPhotoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProductPhoto not found"));
    }
}

