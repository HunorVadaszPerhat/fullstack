package com.example.backend.domain.model.production.productdocument;

import com.example.backend.domain.model.production.document.Document;
import com.example.backend.domain.model.production.product.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "ProductDocument", schema = "Production")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDocument {

    @EmbeddedId
    private ProductDocumentId id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product;

    @ManyToOne
    @MapsId("documentNode")
    @JoinColumn(name = "DocumentNode", nullable = false)
    private Document document;

    @UpdateTimestamp
    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;
}

