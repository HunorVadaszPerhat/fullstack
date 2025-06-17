package com.example.backend.mapper.production.productdescription;

import com.example.backend.domain.model.production.productdescription.ProductDescription;
import com.example.backend.dto.production.productdescription.ProductDescriptionDto;
import org.springframework.stereotype.Component;

@Component
public class ProductDescriptionMapper {

    public ProductDescriptionDto toDto(ProductDescription entity) {
        if (entity == null) return null;

        return ProductDescriptionDto.builder()
                .productDescriptionId(entity.getProductDescriptionId())
                .description(entity.getDescription())
                .rowguid(entity.getRowguid())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public ProductDescription toEntity(ProductDescriptionDto dto) {
        if (dto == null) return null;

        return ProductDescription.builder()
                .productDescriptionId(dto.getProductDescriptionId())
                .description(dto.getDescription())
                .rowguid(dto.getRowguid())
                .modifiedDate(dto.getModifiedDate())
                .build();
    }

    public void updateEntityFromDto(ProductDescriptionDto dto, ProductDescription entity) {
        if (dto == null || entity == null) return;

        entity.setDescription(dto.getDescription());
        entity.setModifiedDate(dto.getModifiedDate());
    }
}

