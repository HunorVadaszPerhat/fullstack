package com.example.backend.service.production.productmodelillustration;

import com.example.backend.dto.production.productmodelillustration.ProductModelIllustrationDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductModelIllustrationService {
    List<ProductModelIllustrationDto> getAll();
    PagedResponse<ProductModelIllustrationDto> getPaginated(Pageable pageable);
    ProductModelIllustrationDto getById(Integer productModelId, Integer illustrationId);
    ProductModelIllustrationDto create(ProductModelIllustrationDto dto);
    ProductModelIllustrationDto update(Integer productModelId, Integer illustrationId, ProductModelIllustrationDto dto);
    void delete(Integer productModelId, Integer illustrationId);
}

