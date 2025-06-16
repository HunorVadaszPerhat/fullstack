package com.example.backend.mapper.production.productcosthistory;

import com.example.backend.domain.model.production.product.Product;
import com.example.backend.domain.model.production.productcosthistory.ProductCostHistory;
import com.example.backend.domain.model.production.productcosthistory.ProductCostHistoryId;
import com.example.backend.dto.production.productcosthistory.ProductCostHistoryDto;
import org.springframework.stereotype.Component;

@Component
public class ProductCostHistoryMapper {

    public ProductCostHistoryDto toDto(ProductCostHistory entity) {
        if (entity == null) return null;

        return ProductCostHistoryDto.builder()
                .productId(entity.getId().getProductId())
                .startDate(entity.getId().getStartDate())
                .endDate(entity.getEndDate())
                .standardCost(entity.getStandardCost())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public ProductCostHistory toEntity(ProductCostHistoryDto dto, Product product) {
        if (dto == null || product == null) return null;

        return ProductCostHistory.builder()
                .id(new ProductCostHistoryId(dto.getProductId(), dto.getStartDate()))
                .product(product)
                .endDate(dto.getEndDate())
                .standardCost(dto.getStandardCost())
                .modifiedDate(dto.getModifiedDate())
                .build();
    }

    public void updateEntityFromDto(ProductCostHistoryDto dto, ProductCostHistory entity) {
        if (dto == null || entity == null) return;

        entity.setEndDate(dto.getEndDate());
        entity.setStandardCost(dto.getStandardCost());
        entity.setModifiedDate(dto.getModifiedDate());
    }
}

