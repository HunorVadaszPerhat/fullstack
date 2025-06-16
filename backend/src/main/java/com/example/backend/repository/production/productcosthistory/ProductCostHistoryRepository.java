package com.example.backend.repository.production.productcosthistory;

import com.example.backend.domain.model.production.productcosthistory.ProductCostHistory;
import com.example.backend.domain.model.production.productcosthistory.ProductCostHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCostHistoryRepository extends JpaRepository<ProductCostHistory, ProductCostHistoryId> {
}
