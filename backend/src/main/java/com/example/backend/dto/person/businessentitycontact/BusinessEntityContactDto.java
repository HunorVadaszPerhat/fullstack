package com.example.backend.dto.person.businessentitycontact;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessEntityContactDto {
    private Integer businessEntityId;
    private Integer personId;
    private Integer contactTypeId;
    private UUID rowguid;
    private LocalDateTime modifiedDate;
}

