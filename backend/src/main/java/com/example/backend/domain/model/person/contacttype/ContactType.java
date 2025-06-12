package com.example.backend.domain.model.person.contacttype;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
/**
 * Lookup table containing the types of business entity contacts.
 */
@Entity
@Table(name = "ContactType", schema = "Person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ContactTypeID", nullable = false)
    private Integer contactTypeId;

    @Column(name = "Name", nullable = false, length = 50)
    private String name;

    @UpdateTimestamp
    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;
}
