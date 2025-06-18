package com.example.backend.mapper.production.productmodel;

import com.example.backend.domain.model.production.productmodel.ProductModel;
import com.example.backend.dto.production.productmodel.ProductModelDto;
import org.springframework.stereotype.Component;

@Component
public class ProductModelMapper {

    public ProductModelDto toDto(ProductModel entity) {
        if (entity == null) return null;

        return ProductModelDto.builder()
                .productModelId(entity.getProductModelId())
                .name(entity.getName())
                .catalogDescription(entity.getCatalogDescription())
                .instructions(entity.getInstructions())
                .rowguid(entity.getRowguid())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public ProductModel toEntity(ProductModelDto dto) {
        if (dto == null) return null;

        return ProductModel.builder()
                .productModelId(dto.getProductModelId())
                .name(dto.getName())
                .catalogDescription(dto.getCatalogDescription())
                .instructions(dto.getInstructions())
                .rowguid(dto.getRowguid())
                .modifiedDate(dto.getModifiedDate())
                .build();
    }

    public void updateEntityFromDto(ProductModelDto dto, ProductModel entity) {
        if (dto == null || entity == null) return;

        entity.setName(dto.getName());
        entity.setCatalogDescription(dto.getCatalogDescription());
        entity.setInstructions(dto.getInstructions());
    }
}
