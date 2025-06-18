package com.example.backend.dto.production.productproductphoto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductProductPhotoDto {
    private Integer productId;
    private Integer productPhotoId;
    private Boolean isPrimary;
    private LocalDateTime modifiedDate;
}


