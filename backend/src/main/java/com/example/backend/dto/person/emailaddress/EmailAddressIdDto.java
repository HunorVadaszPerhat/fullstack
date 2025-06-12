package com.example.backend.dto.person.emailaddress;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailAddressIdDto implements Serializable {
    private Integer businessEntityId;
    private Integer emailAddressId;
}