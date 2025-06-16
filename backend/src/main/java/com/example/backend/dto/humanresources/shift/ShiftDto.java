package com.example.backend.dto.humanresources.shift;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Work shift with start/end time")
public class ShiftDto {

    @Schema(description = "Primary key for shift")
    private Byte shiftId;

    @Schema(description = "Shift name (e.g. Morning, Evening)")
    private String name;

    @Schema(description = "Shift start time")
    private LocalTime startTime;

    @Schema(description = "Shift end time")
    private LocalTime endTime;

    @Schema(description = "Last modified timestamp")
    private LocalDateTime modifiedDate;
}