package com.example.backend.dto.person.businessentityaddress;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessEntityAddressDto {
    private Integer businessEntityID;
    private Integer addressID;
    private Integer addressTypeID;
    private UUID rowguid;
    private LocalDateTime modifiedDate;
}

