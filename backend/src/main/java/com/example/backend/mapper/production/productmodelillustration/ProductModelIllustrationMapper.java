package com.example.backend.mapper.production.productmodelillustration;

import com.example.backend.domain.model.production.productmodelillustration.ProductModelIllustration;
import com.example.backend.domain.model.production.productmodelillustration.ProductModelIllustrationId;
import com.example.backend.dto.production.productmodelillustration.ProductModelIllustrationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductModelIllustrationMapper {

    private final ProductModelIllustrationResolver resolver;

    public ProductModelIllustrationDto toDto(ProductModelIllustration entity) {
        if (entity == null) return null;
        return ProductModelIllustrationDto.builder()
                .productModelId(entity.getId().getProductModelId())
                .illustrationId(entity.getId().getIllustrationId())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public ProductModelIllustration toEntity(ProductModelIllustrationDto dto) {
        if (dto == null) return null;
        return ProductModelIllustration.builder()
                .id(new ProductModelIllustrationId(dto.getProductModelId(), dto.getIllustrationId()))
                .productModel(resolver.resolveProductModel(dto.getProductModelId()))
                .illustration(resolver.resolveIllustration(dto.getIllustrationId()))
                .build();
    }

    public void updateEntityFromDto(ProductModelIllustrationDto dto, ProductModelIllustration entity) {
        entity.setProductModel(resolver.resolveProductModel(dto.getProductModelId()));
        entity.setIllustration(resolver.resolveIllustration(dto.getIllustrationId()));
    }
}
