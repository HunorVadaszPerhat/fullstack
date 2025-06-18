package com.example.backend.repository.production.productmodel;

import com.example.backend.domain.model.production.productmodel.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductModelRepository extends JpaRepository<ProductModel, Integer> {
}
