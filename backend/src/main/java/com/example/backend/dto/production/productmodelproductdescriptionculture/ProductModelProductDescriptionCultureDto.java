package com.example.backend.dto.production.productmodelproductdescriptionculture;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductModelProductDescriptionCultureDto {
    private Integer productModelId;
    private Integer productDescriptionId;
    private String cultureId;
    private LocalDateTime modifiedDate;
}
