package com.example.backend.mapper.production.productphoto;

import com.example.backend.domain.model.production.productphoto.ProductPhoto;
import com.example.backend.dto.production.productphoto.ProductPhotoDto;
import org.springframework.stereotype.Component;

@Component
public class ProductPhotoMapper {

    public ProductPhotoDto toDto(ProductPhoto entity) {
        return ProductPhotoDto.builder()
                .productPhotoId(entity.getProductPhotoId())
                .thumbNailPhoto(entity.getThumbNailPhoto())
                .thumbnailPhotoFileName(entity.getThumbnailPhotoFileName())
                .largePhoto(entity.getLargePhoto())
                .largePhotoFileName(entity.getLargePhotoFileName())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public ProductPhoto toEntity(ProductPhotoDto dto) {
        return ProductPhoto.builder()
                .productPhotoId(dto.getProductPhotoId())
                .thumbNailPhoto(dto.getThumbNailPhoto())
                .thumbnailPhotoFileName(dto.getThumbnailPhotoFileName())
                .largePhoto(dto.getLargePhoto())
                .largePhotoFileName(dto.getLargePhotoFileName())
                .modifiedDate(dto.getModifiedDate())
                .build();
    }

    public void updateEntityFromDto(ProductPhotoDto dto, ProductPhoto entity) {
        entity.setThumbNailPhoto(dto.getThumbNailPhoto());
        entity.setThumbnailPhotoFileName(dto.getThumbnailPhotoFileName());
        entity.setLargePhoto(dto.getLargePhoto());
        entity.setLargePhotoFileName(dto.getLargePhotoFileName());
    }
}
