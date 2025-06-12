package com.example.backend.dto.person.phonenumbertype;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneNumberTypeDto {
    private Integer phoneNumberTypeId;
    private String name;
    private LocalDateTime modifiedDate;
}
