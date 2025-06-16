package com.example.backend.domain.model.production.productcosthistory;

import com.example.backend.domain.model.production.product.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ProductCostHistory", schema = "Production")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCostHistory {

    @EmbeddedId
    private ProductCostHistoryId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product;

    @Column(name = "EndDate")
    private LocalDateTime endDate;

    @Column(name = "StandardCost", nullable = false)
    private BigDecimal standardCost;

    @UpdateTimestamp
    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;
}

