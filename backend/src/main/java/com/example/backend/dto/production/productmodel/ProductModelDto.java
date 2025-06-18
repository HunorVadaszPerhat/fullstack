package com.example.backend.dto.production.productmodel;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductModelDto {

    private Integer productModelId;
    private String name;
    private String catalogDescription;
    private String instructions;
    private UUID rowguid;
    private LocalDateTime modifiedDate;
}

