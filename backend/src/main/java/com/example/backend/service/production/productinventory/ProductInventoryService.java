package com.example.backend.service.production.productinventory;

import com.example.backend.dto.production.productinventory.ProductInventoryDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductInventoryService {
    ProductInventoryDto create(ProductInventoryDto dto);
    ProductInventoryDto getById(Integer productId, Integer locationId);
    List<ProductInventoryDto> getAll();
    PagedResponse<ProductInventoryDto> getPaginated(Pageable pageable);
    ProductInventoryDto update(Integer productId, Integer locationId, ProductInventoryDto dto);
    void delete(Integer productId, Integer locationId);
}

