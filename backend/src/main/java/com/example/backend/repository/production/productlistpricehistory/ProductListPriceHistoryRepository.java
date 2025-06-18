package com.example.backend.repository.production.productlistpricehistory;

import com.example.backend.domain.model.production.productlistpricehistory.ProductListPriceHistory;
import com.example.backend.domain.model.production.productlistpricehistory.ProductListPriceHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductListPriceHistoryRepository extends JpaRepository<ProductListPriceHistory, ProductListPriceHistoryId> {
}
