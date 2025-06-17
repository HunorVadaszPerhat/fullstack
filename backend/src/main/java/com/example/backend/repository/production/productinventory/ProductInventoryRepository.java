package com.example.backend.repository.production.productinventory;

import com.example.backend.domain.model.production.productiventory.ProductInventory;
import com.example.backend.domain.model.production.productiventory.ProductInventoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInventoryRepository extends JpaRepository<ProductInventory, ProductInventoryId> {
}

