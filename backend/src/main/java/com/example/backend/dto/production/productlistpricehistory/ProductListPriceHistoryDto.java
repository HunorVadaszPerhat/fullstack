package com.example.backend.dto.production.productlistpricehistory;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductListPriceHistoryDto {

    private Integer productId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal listPrice;
    private LocalDateTime modifiedDate;
}

