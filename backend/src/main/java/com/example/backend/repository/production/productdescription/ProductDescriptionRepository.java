package com.example.backend.repository.production.productdescription;

import com.example.backend.domain.model.production.productdescription.ProductDescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDescriptionRepository extends JpaRepository<ProductDescription, Integer> {
}

