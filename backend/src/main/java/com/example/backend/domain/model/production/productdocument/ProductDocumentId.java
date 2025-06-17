package com.example.backend.domain.model.production.productdocument;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductDocumentId implements Serializable {

    @Column(name = "ProductID", nullable = false)
    private Integer productId;

    @Column(name = "DocumentNode", nullable = false, columnDefinition = "hierarchyid")
    private String documentNode; // mapping hierarchyid as String for now
}

