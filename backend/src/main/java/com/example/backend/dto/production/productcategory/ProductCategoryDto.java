package com.example.backend.dto.production.productcategory;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCategoryDto {
    private Integer productCategoryId;
    private String name;
    private UUID rowguid;
    private LocalDateTime modifiedDate;
}

