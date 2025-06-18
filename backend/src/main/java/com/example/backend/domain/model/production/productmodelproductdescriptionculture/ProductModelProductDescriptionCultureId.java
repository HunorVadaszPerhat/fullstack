package com.example.backend.domain.model.production.productmodelproductdescriptionculture;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductModelProductDescriptionCultureId implements Serializable {
    private Integer productModelId;
    private Integer productDescriptionId;
    private String cultureId;
}

