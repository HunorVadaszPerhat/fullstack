package com.example.backend.dto.production.productdocument;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDocumentDto {

    private Integer productId;
    private String documentNode;
    private LocalDateTime modifiedDate;
}

