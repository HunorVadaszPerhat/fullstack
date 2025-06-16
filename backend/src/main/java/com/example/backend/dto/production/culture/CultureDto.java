package com.example.backend.dto.production.culture;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CultureDto {
    private String cultureId;
    private String name;
    private LocalDateTime modifiedDate;
}
