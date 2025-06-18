package com.example.backend.service.production.productmodel;

import com.example.backend.dto.production.productmodel.ProductModelDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductModelService {
    List<ProductModelDto> getAll();
    PagedResponse<ProductModelDto> getPaginated(Pageable pageable);
    ProductModelDto getById(Integer id);
    ProductModelDto create(ProductModelDto dto);
    ProductModelDto update(Integer id, ProductModelDto dto);
    void delete(Integer id);
}
