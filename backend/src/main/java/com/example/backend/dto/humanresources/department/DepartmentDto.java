package com.example.backend.dto.humanresources.department;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data transfer object for Department")
public class DepartmentDto {

    @Schema(description = "Department ID (auto-generated)", example = "5")
    private Short departmentId;

    @Schema(description = "Unique name of the department", example = "Engineering")
    private String name;

    @Schema(description = "Name of the group to which this department belongs", example = "Product Development")
    private String groupName;

    @Schema(description = "Timestamp of last modification", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime modifiedDate;
}

