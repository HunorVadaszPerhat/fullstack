package com.example.backend.dto.person.password;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordDto {
    private Integer personId;  // maps to BusinessEntityID
    private String passwordHash;
    private String passwordSalt;
    private UUID rowguid;
    private LocalDateTime modifiedDate;
}

