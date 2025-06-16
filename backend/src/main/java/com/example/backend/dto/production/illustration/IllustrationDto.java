package com.example.backend.dto.production.illustration;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IllustrationDto {
    private Integer illustrationId;
    private String diagram;
    private LocalDateTime modifiedDate;
}
