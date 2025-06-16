package com.example.backend.dto.production.product;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private Integer productId;
    private String name;
    private String productNumber;
    private Boolean makeFlag;
    private Boolean finishedGoodsFlag;
    private String color;
    private Short safetyStockLevel;
    private Short reorderPoint;
    private BigDecimal standardCost;
    private BigDecimal listPrice;
    private String size;
    private String sizeUnitMeasureCode;
    private String weightUnitMeasureCode;
    private BigDecimal weight;
    private Integer daysToManufacture;
    private String productLine;
    private String clazz;
    private String style;
    private Integer productSubcategoryId;
    private Integer productModelId;
    private LocalDateTime sellStartDate;
    private LocalDateTime sellEndDate;
    private LocalDateTime discontinuedDate;
    private UUID rowguid;
    private LocalDateTime modifiedDate;
}

