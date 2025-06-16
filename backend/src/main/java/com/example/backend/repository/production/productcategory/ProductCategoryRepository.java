package com.example.backend.repository.production.productcategory;

import com.example.backend.domain.model.production.productcategory.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
}

