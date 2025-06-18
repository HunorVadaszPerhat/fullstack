package com.example.backend.domain.model.production.productmodelproductdescriptionculture;

import com.example.backend.domain.model.production.culture.Culture;
import com.example.backend.domain.model.production.productdescription.ProductDescription;
import com.example.backend.domain.model.production.productmodel.ProductModel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ProductModelProductDescriptionCulture", schema = "Production")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductModelProductDescriptionCulture {

    @EmbeddedId
    private ProductModelProductDescriptionCultureId id;

    @ManyToOne
    @MapsId("productModelId")
    @JoinColumn(name = "ProductModelID", referencedColumnName = "ProductModelID")
    private ProductModel productModel;

    @ManyToOne
    @MapsId("productDescriptionId")
    @JoinColumn(name = "ProductDescriptionID", referencedColumnName = "ProductDescriptionID")
    private ProductDescription productDescription;

    @ManyToOne
    @MapsId("cultureId")
    @JoinColumn(name = "CultureID", referencedColumnName = "CultureID")
    private Culture culture;

    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;
}
