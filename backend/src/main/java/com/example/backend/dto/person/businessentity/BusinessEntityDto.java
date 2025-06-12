package com.example.backend.dto.person.businessentity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessEntityDto {
    private Integer businessEntityId;
    private UUID rowguid;
    private LocalDateTime modifiedDate;
}