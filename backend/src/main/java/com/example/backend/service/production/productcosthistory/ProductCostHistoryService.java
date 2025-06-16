package com.example.backend.service.production.productcosthistory;

import com.example.backend.dto.production.productcosthistory.ProductCostHistoryDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductCostHistoryService {
    List<ProductCostHistoryDto> getAll();
    PagedResponse<ProductCostHistoryDto> getPaginated(Pageable pageable);
    ProductCostHistoryDto getById(Integer productId, LocalDateTime startDate);
    ProductCostHistoryDto create(ProductCostHistoryDto dto);
    ProductCostHistoryDto update(Integer productId, LocalDateTime startDate, ProductCostHistoryDto dto);
    void delete(Integer productId, LocalDateTime startDate);
}

