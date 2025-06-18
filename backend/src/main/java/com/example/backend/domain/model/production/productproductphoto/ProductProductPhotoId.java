package com.example.backend.domain.model.production.productproductphoto;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductProductPhotoId implements Serializable {
    private Integer productId;
    private Integer productPhotoId;
}


