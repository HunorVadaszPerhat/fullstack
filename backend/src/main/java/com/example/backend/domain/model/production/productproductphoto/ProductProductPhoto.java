package com.example.backend.domain.model.production.productproductphoto;

import com.example.backend.domain.model.production.product.Product;
import com.example.backend.domain.model.production.productphoto.ProductPhoto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ProductProductPhoto", schema = "Production")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductProductPhoto {

    @EmbeddedId
    private ProductProductPhotoId id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "ProductID")
    private Product product;

    @ManyToOne
    @MapsId("productPhotoId")
    @JoinColumn(name = "ProductPhotoID")
    private ProductPhoto productPhoto;

    @Column(name = "Primary", nullable = false)
    private Boolean isPrimary;

    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;
}
