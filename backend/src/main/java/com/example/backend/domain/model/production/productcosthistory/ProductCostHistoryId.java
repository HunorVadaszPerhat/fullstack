package com.example.backend.domain.model.production.productcosthistory;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductCostHistoryId implements Serializable {

    @Column(name = "ProductID", nullable = false)
    private Integer productId;

    @Column(name = "StartDate", nullable = false)
    private LocalDateTime startDate;
}

