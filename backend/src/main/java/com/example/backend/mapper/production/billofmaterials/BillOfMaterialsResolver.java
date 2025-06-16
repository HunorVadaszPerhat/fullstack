package com.example.backend.mapper.production.billofmaterials;

import com.example.backend.domain.model.production.product.Product;
import com.example.backend.repository.production.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BillOfMaterialsResolver {
    private final ProductRepository productRepository;
    private final UnitMeasureRepository unitMeasureRepository;

    public Product resolveProduct(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + id));
    }

    public UnitMeasure resolveUnitMeasure(String code) {
        return unitMeasureRepository.findById(code)
                .orElseThrow(() -> new EntityNotFoundException("UnitMeasure not found: " + code));
    }
}

