package com.example.backend.service.production.productlistpricehistory;

import com.example.backend.dto.production.productlistpricehistory.ProductListPriceHistoryDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductListPriceHistoryService {
    List<ProductListPriceHistoryDto> getAll();
    PagedResponse<ProductListPriceHistoryDto> getPaginated(Pageable pageable);
    ProductListPriceHistoryDto getById(Integer productId, LocalDateTime startDate);
    ProductListPriceHistoryDto create(ProductListPriceHistoryDto dto);
    ProductListPriceHistoryDto update(Integer productId, LocalDateTime startDate, ProductListPriceHistoryDto dto);
    void delete(Integer productId, LocalDateTime startDate);
}

