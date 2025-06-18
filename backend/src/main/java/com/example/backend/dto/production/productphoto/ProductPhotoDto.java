package com.example.backend.dto.production.productphoto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPhotoDto {
    private Integer productPhotoId;
    private byte[] thumbNailPhoto;
    private String thumbnailPhotoFileName;
    private byte[] largePhoto;
    private String largePhotoFileName;
    private LocalDateTime modifiedDate;
}
