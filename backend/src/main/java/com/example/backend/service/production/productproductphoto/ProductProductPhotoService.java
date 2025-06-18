package com.example.backend.service.production.productproductphoto;

import com.example.backend.domain.model.production.productproductphoto.ProductProductPhotoId;
import com.example.backend.dto.production.productproductphoto.ProductProductPhotoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductProductPhotoService {
    ProductProductPhotoDto create(ProductProductPhotoDto dto);
    ProductProductPhotoDto getById(ProductProductPhotoId id);
    List<ProductProductPhotoDto> getAll();
    Page<ProductProductPhotoDto> getPaginated(Pageable pageable);
    ProductProductPhotoDto update(ProductProductPhotoId id, ProductProductPhotoDto dto);
    void delete(ProductProductPhotoId id);
}

