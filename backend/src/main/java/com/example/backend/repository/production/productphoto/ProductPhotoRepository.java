package com.example.backend.repository.production.productphoto;

import com.example.backend.domain.model.production.productphoto.ProductPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPhotoRepository extends JpaRepository<ProductPhoto, Integer> {
}

