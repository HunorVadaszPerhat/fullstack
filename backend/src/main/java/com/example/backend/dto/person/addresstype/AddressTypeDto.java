package com.example.backend.dto.person.addresstype;

import lombok.*;

import java.util.Date;
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
    private Date modifiedDate;
}
