package com.example.backend.mapper.production.productinventory;

import com.example.backend.domain.model.production.location.Location;
import com.example.backend.domain.model.production.product.Product;
import com.example.backend.domain.model.production.productiventory.ProductInventory;
import com.example.backend.domain.model.production.productiventory.ProductInventoryId;
import com.example.backend.dto.production.productinventory.ProductInventoryDto;
import org.springframework.stereotype.Component;

@Component
public class ProductInventoryMapper {

    public ProductInventoryDto toDto(ProductInventory entity) {
        return ProductInventoryDto.builder()
                .productId(entity.getId().getProductId())
                .locationId(entity.getId().getLocationId())
                .shelf(entity.getShelf())
                .bin(entity.getBin())
                .quantity(entity.getQuantity())
                .rowguid(entity.getRowguid())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public ProductInventory toEntity(ProductInventoryDto dto, Product product, Location location) {
        return ProductInventory.builder()
                .id(new ProductInventoryId(dto.getProductId(), dto.getLocationId()))
                .product(product)
                .location(location)
                .shelf(dto.getShelf())
                .bin(dto.getBin())
                .quantity(dto.getQuantity())
                .rowguid(dto.getRowguid())
                .build();
    }

    public void updateEntity(ProductInventory entity, ProductInventoryDto dto) {
        entity.setShelf(dto.getShelf());
        entity.setBin(dto.getBin());
        entity.setQuantity(dto.getQuantity());
    }
}

