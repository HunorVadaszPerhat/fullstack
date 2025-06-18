package com.example.backend.mapper.production.productproductphoto;

import com.example.backend.domain.model.production.productproductphoto.ProductProductPhoto;
import com.example.backend.domain.model.production.productproductphoto.ProductProductPhotoId;
import com.example.backend.dto.production.productproductphoto.ProductProductPhotoDto;
import org.springframework.stereotype.Component;

@Component
public class ProductProductPhotoMapper {

    public ProductProductPhotoDto toDto(ProductProductPhoto entity) {
        return ProductProductPhotoDto.builder()
                .productId(entity.getProduct().getId())
                .productPhotoId(entity.getProductPhoto().getId())
                .isPrimary(entity.getIsPrimary())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public ProductProductPhoto toEntity(ProductProductPhotoDto dto, ProductProductPhotoResolver resolver) {
        return ProductProductPhoto.builder()
                .id(new ProductProductPhotoId(dto.getProductId(), dto.getProductPhotoId()))
                .product(resolver.resolveProduct(dto.getProductId()))
                .productPhoto(resolver.resolveProductPhoto(dto.getProductPhotoId()))
                .isPrimary(dto.getIsPrimary())
                .modifiedDate(dto.getModifiedDate())
                .build();
    }
}
