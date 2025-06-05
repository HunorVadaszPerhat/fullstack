package com.example.backend.domain.model.person.contacttype;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Lookup table containing the types of business entity contacts.
 */
@Entity
@Table(name = "ContactType",
        uniqueConstraints = @UniqueConstraint(name = "AK_ContactType_Name", columnNames = "Name"),
        schema = "Person"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactType {

    /**
     * Primary key for ContactType records.
     * Clustered index created by a primary key constraint.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ContactTypeID")
    private Integer contactTypeId;

    /**
     * Contact type description.
     */
    @Column(name = "Name", nullable = false, unique = true, length = 50)
    private String name;

    /**
     * Date and time the record was last updated.
     * Automatically set when the entity is first created.
     */
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ModifiedDate", nullable = false, columnDefinition = "datetime default getdate()")
    private Date modifiedDate;
}
