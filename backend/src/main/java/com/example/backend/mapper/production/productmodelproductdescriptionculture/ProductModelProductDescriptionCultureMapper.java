package com.example.backend.mapper.production.productmodelproductdescriptionculture;

import com.example.backend.domain.model.production.culture.Culture;
import com.example.backend.domain.model.production.productdescription.ProductDescription;
import com.example.backend.domain.model.production.productmodel.ProductModel;
import com.example.backend.domain.model.production.productmodelproductdescriptionculture.ProductModelProductDescriptionCulture;
import com.example.backend.domain.model.production.productmodelproductdescriptionculture.ProductModelProductDescriptionCultureId;
import com.example.backend.dto.production.productmodelproductdescriptionculture.ProductModelProductDescriptionCultureDto;
import org.springframework.stereotype.Component;

@Component
public class ProductModelProductDescriptionCultureMapper {

    public ProductModelProductDescriptionCultureDto toDto(ProductModelProductDescriptionCulture entity) {
        return ProductModelProductDescriptionCultureDto.builder()
                .productModelId(entity.getId().getProductModelId())
                .productDescriptionId(entity.getId().getProductDescriptionId())
                .cultureId(entity.getId().getCultureId())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public ProductModelProductDescriptionCulture toEntity(ProductModelProductDescriptionCultureDto dto,
                                                          ProductModel productModel,
                                                          ProductDescription productDescription,
                                                          Culture culture) {
        ProductModelProductDescriptionCultureId id = new ProductModelProductDescriptionCultureId(
                dto.getProductModelId(), dto.getProductDescriptionId(), dto.getCultureId()
        );

        return ProductModelProductDescriptionCulture.builder()
                .id(id)
                .productModel(productModel)
                .productDescription(productDescription)
                .culture(culture)
                .modifiedDate(dto.getModifiedDate())
                .build();
    }
}

