package com.example.backend.service.production.productphoto;

import com.example.backend.dto.production.productphoto.ProductPhotoDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductPhotoService {
    ProductPhotoDto create(ProductPhotoDto dto);
    ProductPhotoDto getById(Integer id);
    List<ProductPhotoDto> getAll();
    PagedResponse<ProductPhotoDto> getPaginated(Pageable pageable);
    ProductPhotoDto update(Integer id, ProductPhotoDto dto);
    void delete(Integer id);
}

