package com.example.backend.domain.model.production.productmodelillustration;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductModelIllustrationId implements Serializable {

    @Column(name = "ProductModelID", nullable = false)
    private Integer productModelId;

    @Column(name = "IllustrationID", nullable = false)
    private Integer illustrationId;
}

