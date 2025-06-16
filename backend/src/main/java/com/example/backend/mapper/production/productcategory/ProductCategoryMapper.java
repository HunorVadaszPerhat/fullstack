package com.example.backend.mapper.production.productcategory;

import com.example.backend.domain.model.production.productcategory.ProductCategory;
import com.example.backend.dto.production.productcategory.ProductCategoryDto;
import org.springframework.stereotype.Component;

@Component
public class ProductCategoryMapper {

    public ProductCategoryDto toDto(ProductCategory entity) {
        if (entity == null) return null;
        return ProductCategoryDto.builder()
                .productCategoryId(entity.getProductCategoryId())
                .name(entity.getName())
                .rowguid(entity.getRowguid())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public ProductCategory toEntity(ProductCategoryDto dto) {
        if (dto == null) return null;
        return ProductCategory.builder()
                .productCategoryId(dto.getProductCategoryId())
                .name(dto.getName())
                .rowguid(dto.getRowguid())
                .modifiedDate(dto.getModifiedDate())
                .build();
    }

    public void updateEntityFromDto(ProductCategoryDto dto, ProductCategory entity) {
        if (dto == null || entity == null) return;
        entity.setName(dto.getName());
        entity.setRowguid(dto.getRowguid());
        entity.setModifiedDate(dto.getModifiedDate());
    }
}

