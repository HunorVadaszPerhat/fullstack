package com.example.backend.service.production.productdescription;

import com.example.backend.dto.production.productdescription.ProductDescriptionDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductDescriptionService {
    List<ProductDescriptionDto> getAll();
    PagedResponse<ProductDescriptionDto> getPaginated(Pageable pageable);
    ProductDescriptionDto getById(Integer id);
    ProductDescriptionDto create(ProductDescriptionDto dto);
    ProductDescriptionDto update(Integer id, ProductDescriptionDto dto);
    void delete(Integer id);
}

