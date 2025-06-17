package com.example.backend.dto.production.productdescription;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDescriptionDto {

    private Integer productDescriptionId;
    private String description;
    private UUID rowguid;
    private LocalDateTime modifiedDate;
}

