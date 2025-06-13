package com.example.backend.dto.person.personphone;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonPhoneDto {
    private Integer businessEntityId;
    private String phoneNumber;
    private Integer phoneNumberTypeId;
    private LocalDateTime modifiedDate;
}

