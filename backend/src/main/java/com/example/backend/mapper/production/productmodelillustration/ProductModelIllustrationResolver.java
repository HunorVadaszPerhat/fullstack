package com.example.backend.mapper.production.productmodelillustration;

import com.example.backend.domain.model.production.illustration.Illustration;
import com.example.backend.domain.model.production.productmodel.ProductModel;
import com.example.backend.repository.production.illustration.IllustrationRepository;
import com.example.backend.repository.production.productmodel.ProductModelRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductModelIllustrationResolver {

    private final ProductModelRepository productModelRepository;
    private final IllustrationRepository illustrationRepository;

    public ProductModel resolveProductModel(Integer id) {
        return productModelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductModel not found: " + id));
    }

    public Illustration resolveIllustration(Integer id) {
        return illustrationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Illustration not found: " + id));
    }
}
