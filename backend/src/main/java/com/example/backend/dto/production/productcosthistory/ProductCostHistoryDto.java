package com.example.backend.dto.production.productcosthistory;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCostHistoryDto {

    private Integer productId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal standardCost;
    private LocalDateTime modifiedDate;
}

