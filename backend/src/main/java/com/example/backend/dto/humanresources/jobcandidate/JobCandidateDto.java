package com.example.backend.dto.humanresources.jobcandidate;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobCandidateDto {
    private Integer jobCandidateId;
    private Integer employeeId;
    private String resume;
    private LocalDateTime modifiedDate;
}
