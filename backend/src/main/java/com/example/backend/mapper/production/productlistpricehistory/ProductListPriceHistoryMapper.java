package com.example.backend.mapper.production.productlistpricehistory;

import com.example.backend.domain.model.production.product.Product;
import com.example.backend.domain.model.production.productlistpricehistory.ProductListPriceHistory;
import com.example.backend.domain.model.production.productlistpricehistory.ProductListPriceHistoryId;
import com.example.backend.dto.production.productlistpricehistory.ProductListPriceHistoryDto;
import org.springframework.stereotype.Component;

@Component
public class ProductListPriceHistoryMapper {

    public ProductListPriceHistoryDto toDto(ProductListPriceHistory entity) {
        if (entity == null) return null;

        return ProductListPriceHistoryDto.builder()
                .productId(entity.getId().getProductId())
                .startDate(entity.getId().getStartDate())
                .endDate(entity.getEndDate())
                .listPrice(entity.getListPrice())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public ProductListPriceHistory toEntity(ProductListPriceHistoryDto dto, Product product) {
        if (dto == null || product == null) return null;

        return ProductListPriceHistory.builder()
                .id(new ProductListPriceHistoryId(dto.getProductId(), dto.getStartDate()))
                .product(product)
                .endDate(dto.getEndDate())
                .listPrice(dto.getListPrice())
                .build();
    }

    public void updateEntityFromDto(ProductListPriceHistoryDto dto, ProductListPriceHistory entity) {
        if (dto == null || entity == null) return;

        entity.setEndDate(dto.getEndDate());
        entity.setListPrice(dto.getListPrice());
    }
}
