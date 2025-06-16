package com.example.backend.service.production.product;

import com.example.backend.dto.production.product.ProductDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAll();
    PagedResponse<ProductDto> getPaginated(Pageable pageable);
    ProductDto getById(Integer id);
    ProductDto create(ProductDto dto);
    ProductDto update(Integer id, ProductDto dto);
    void delete(Integer id);
}

