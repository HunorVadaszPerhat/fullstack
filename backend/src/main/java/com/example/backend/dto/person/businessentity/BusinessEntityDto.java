package com.example.backend.dto.person.businessentity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessEntityDto {
    private Integer businessEntityID;
    @NotNull(message = "rowguid is required")
    @Schema(hidden = true)
    private UUID rowguid;

    @NotNull(message = "Modified date is required")
    private Date modifiedDate;
}