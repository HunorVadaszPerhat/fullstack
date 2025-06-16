package com.example.backend.dto.production.billofmaterials;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillOfMaterialsDto {
    private Integer billOfMaterialsId;
    private Integer productAssemblyId;
    private Integer componentId;
    private String unitMeasureCode;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Short bomLevel;
    private BigDecimal perAssemblyQty;
    private LocalDateTime modifiedDate;
}
