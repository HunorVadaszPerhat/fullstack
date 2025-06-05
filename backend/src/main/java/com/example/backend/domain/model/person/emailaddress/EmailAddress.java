package com.example.backend.domain.model.person.emailaddress;

import com.example.backend.domain.model.person.person.Person;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

/**
 * Where to send a person email.
 */
@Entity
@Table(
        name = "EmailAddress",
        schema = "Person",
        indexes = {
                @Index(name = "IX_EmailAddress_EmailAddress", columnList = "EmailAddress")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailAddress {

    @EmbeddedId
    private EmailAddressId id;

    /**
     * Person associated with this email address. Foreign key to Person.BusinessEntityID
     */
    @ManyToOne
    @MapsId("businessEntityId")
    @JoinColumn(name = "BusinessEntityID", nullable = false)
    private Person person;

    /**
     * E-mail address for the person.
     */
    @Column(name = "EmailAddress", length = 50)
    private String emailAddress;

    /**
     * ROWGUIDCOL number uniquely identifying the record. Used to support a merge replication sample.
     */
    @Column(name = "rowguid", nullable = false, columnDefinition = "uniqueidentifier default newid()")
    private UUID rowguid = UUID.randomUUID();

    /**
     * Date and time the record was last updated.
     * Automatically set when the entity is first created.
     */
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ModifiedDate", nullable = false, columnDefinition = "datetime default getdate()")
    private Date modifiedDate;
}
