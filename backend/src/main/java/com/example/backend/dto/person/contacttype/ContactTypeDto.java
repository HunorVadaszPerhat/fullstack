package com.example.backend.dto.person.contacttype;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactTypeDto {
    private Integer contactTypeId;
    private String name;
    private LocalDateTime modifiedDate;
}

