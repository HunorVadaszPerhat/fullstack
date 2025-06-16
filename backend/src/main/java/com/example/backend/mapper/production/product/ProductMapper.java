package com.example.backend.mapper.production.product;

import com.example.backend.domain.model.production.product.Product;
import com.example.backend.dto.production.product.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDto toDto(Product entity) {
        if (entity == null) return null;

        return ProductDto.builder()
                .productId(entity.getProductId())
                .name(entity.getName())
                .productNumber(entity.getProductNumber())
                .makeFlag(entity.getMakeFlag())
                .finishedGoodsFlag(entity.getFinishedGoodsFlag())
                .color(entity.getColor())
                .safetyStockLevel(entity.getSafetyStockLevel())
                .reorderPoint(entity.getReorderPoint())
                .standardCost(entity.getStandardCost())
                .listPrice(entity.getListPrice())
                .size(entity.getSize())
                .sizeUnitMeasureCode(entity.getSizeUnitMeasure() != null ? entity.getSizeUnitMeasure().getUnitMeasureCode() : null)
                .weightUnitMeasureCode(entity.getWeightUnitMeasure() != null ? entity.getWeightUnitMeasure().getUnitMeasureCode() : null)
                .weight(entity.getWeight())
                .daysToManufacture(entity.getDaysToManufacture())
                .productLine(entity.getProductLine())
                .clazz(entity.getClazz())
                .style(entity.getStyle())
                .productSubcategoryId(entity.getProductSubcategory() != null ? entity.getProductSubcategory().getProductSubcategoryId() : null)
                .productModelId(entity.getProductModel() != null ? entity.getProductModel().getProductModelId() : null)
                .sellStartDate(entity.getSellStartDate())
                .sellEndDate(entity.getSellEndDate())
                .discontinuedDate(entity.getDiscontinuedDate())
                .rowguid(entity.getRowguid())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public Product toEntity(ProductDto dto) {
        if (dto == null) return null;

        return Product.builder()
                .productId(dto.getProductId())
                .name(dto.getName())
                .productNumber(dto.getProductNumber())
                .makeFlag(dto.getMakeFlag())
                .finishedGoodsFlag(dto.getFinishedGoodsFlag())
                .color(dto.getColor())
                .safetyStockLevel(dto.getSafetyStockLevel())
                .reorderPoint(dto.getReorderPoint())
                .standardCost(dto.getStandardCost())
                .listPrice(dto.getListPrice())
                .size(dto.getSize())
                .weight(dto.getWeight())
                .daysToManufacture(dto.getDaysToManufacture())
                .productLine(dto.getProductLine())
                .clazz(dto.getClazz())
                .style(dto.getStyle())
                .sellStartDate(dto.getSellStartDate())
                .sellEndDate(dto.getSellEndDate())
                .discontinuedDate(dto.getDiscontinuedDate())
                .rowguid(dto.getRowguid())
                .modifiedDate(dto.getModifiedDate())
                .build();
    }

    public void updateEntityFromDto(ProductDto dto, Product entity) {
        if (dto == null || entity == null) return;

        entity.setName(dto.getName());
        entity.setProductNumber(dto.getProductNumber());
        entity.setMakeFlag(dto.getMakeFlag());
        entity.setFinishedGoodsFlag(dto.getFinishedGoodsFlag());
        entity.setColor(dto.getColor());
        entity.setSafetyStockLevel(dto.getSafetyStockLevel());
        entity.setReorderPoint(dto.getReorderPoint());
        entity.setStandardCost(dto.getStandardCost());
        entity.setListPrice(dto.getListPrice());
        entity.setSize(dto.getSize());
        entity.setWeight(dto.getWeight());
        entity.setDaysToManufacture(dto.getDaysToManufacture());
        entity.setProductLine(dto.getProductLine());
        entity.setClazz(dto.getClazz());
        entity.setStyle(dto.getStyle());
        entity.setSellStartDate(dto.getSellStartDate());
        entity.setSellEndDate(dto.getSellEndDate());
        entity.setDiscontinuedDate(dto.getDiscontinuedDate());
    }
}

