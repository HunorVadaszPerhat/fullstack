package com.example.backend.domain.model.production.illustration;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "Illustration", schema = "Production")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Illustration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IllustrationID", nullable = false)
    private Integer illustrationId;

    @Lob
    @Column(name = "Diagram")
    private String diagram;

    @UpdateTimestamp
    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;
}

