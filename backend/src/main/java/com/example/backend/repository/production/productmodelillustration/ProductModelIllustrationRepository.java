package com.example.backend.repository.production.productmodelillustration;

import com.example.backend.domain.model.production.productmodelillustration.ProductModelIllustration;
import com.example.backend.domain.model.production.productmodelillustration.ProductModelIllustrationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductModelIllustrationRepository extends JpaRepository<ProductModelIllustration, ProductModelIllustrationId> {
}
