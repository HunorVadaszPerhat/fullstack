package com.example.backend.dto.person.emailaddress;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailAddressDto {
    private EmailAddressIdDto id;
    private Integer personId; // flattening the Person reference to just the ID
    private String emailAddress;
    private UUID rowguid;
    private LocalDateTime modifiedDate;
}
