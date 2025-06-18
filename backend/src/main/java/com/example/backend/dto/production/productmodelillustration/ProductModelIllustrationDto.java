package com.example.backend.dto.production.productmodelillustration;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductModelIllustrationDto {
    private Integer productModelId;
    private Integer illustrationId;
    private LocalDateTime modifiedDate;
}

