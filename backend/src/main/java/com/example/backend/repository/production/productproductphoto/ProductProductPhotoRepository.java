package com.example.backend.repository.production.productproductphoto;

import com.example.backend.domain.model.production.productproductphoto.ProductProductPhoto;
import com.example.backend.domain.model.production.productproductphoto.ProductProductPhotoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductProductPhotoRepository extends JpaRepository<ProductProductPhoto, ProductProductPhotoId> {
}

