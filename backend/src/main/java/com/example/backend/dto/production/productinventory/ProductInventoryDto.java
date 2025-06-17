package com.example.backend.dto.production.productinventory;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInventoryDto {
    private Integer productId;
    private Integer locationId;
    private String shelf;
    private Integer bin;
    private Integer quantity;
    private UUID rowguid;
    private LocalDateTime modifiedDate;
}

