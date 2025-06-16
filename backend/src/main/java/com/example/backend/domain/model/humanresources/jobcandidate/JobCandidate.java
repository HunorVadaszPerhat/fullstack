package com.example.backend.domain.model.humanresources.jobcandidate;

import com.example.backend.domain.model.humanresources.employee.Employee;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "JobCandidate", schema = "HumanResources")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobCandidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "JobCandidateID", nullable = false)
    private Integer jobCandidateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BusinessEntityID")
    private Employee employee;

    @Lob
    @Column(name = "Resume")
    private String resume;

    @UpdateTimestamp
    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;
}
