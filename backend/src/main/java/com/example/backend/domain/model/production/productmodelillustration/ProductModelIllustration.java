package com.example.backend.domain.model.production.productmodelillustration;

import com.example.backend.domain.model.production.illustration.Illustration;
import com.example.backend.domain.model.production.productmodel.ProductModel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "ProductModelIllustration", schema = "Production")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductModelIllustration {

    @EmbeddedId
    private ProductModelIllustrationId id;

    @ManyToOne
    @MapsId("productModelId")
    @JoinColumn(name = "ProductModelID", nullable = false)
    private ProductModel productModel;

    @ManyToOne
    @MapsId("illustrationId")
    @JoinColumn(name = "IllustrationID", nullable = false)
    private Illustration illustration;

    @UpdateTimestamp
    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;
}

