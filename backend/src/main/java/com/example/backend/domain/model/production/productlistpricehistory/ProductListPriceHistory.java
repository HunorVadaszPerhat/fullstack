package com.example.backend.domain.model.production.productlistpricehistory;

import com.example.backend.domain.model.production.product.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ProductListPriceHistory", schema = "Production")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductListPriceHistory {

    @EmbeddedId
    private ProductListPriceHistoryId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product;

    @Column(name = "EndDate")
    private LocalDateTime endDate;

    @Column(name = "ListPrice", nullable = false)
    private BigDecimal listPrice;

    @UpdateTimestamp
    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;
}
