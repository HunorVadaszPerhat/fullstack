package com.example.backend.dto.person.addresstype;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressTypeDto {
    private Integer addressTypeId;
    private String name;
    private UUID rowguid;
    private LocalDateTime modifiedDate;
}
