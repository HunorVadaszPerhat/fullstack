package com.example.backend.service.production.productcategory;

import com.example.backend.dto.production.productcategory.ProductCategoryDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategoryDto> getAll();
    PagedResponse<ProductCategoryDto> getPaginated(Pageable pageable);
    ProductCategoryDto getById(Integer id);
    ProductCategoryDto create(ProductCategoryDto dto);
    ProductCategoryDto update(Integer id, ProductCategoryDto dto);
    void delete(Integer id);
}
