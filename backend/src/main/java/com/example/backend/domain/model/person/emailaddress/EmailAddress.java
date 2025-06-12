package com.example.backend.domain.model.person.emailaddress;

import com.example.backend.domain.model.person.person.Person;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;
/**
 * Where to send a person email.
 */
@Entity
@Table(name = "EmailAddress", schema = "Person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailAddress {

    @EmbeddedId
    private EmailAddressId id;

    @ManyToOne
    @MapsId("businessEntityId")
    @JoinColumn(name = "BusinessEntityID", nullable = false)
    private Person person;

    @Column(name = "EmailAddress", length = 50)
    private String emailAddress;

    @Column(name = "rowguid", nullable = false)
    private UUID rowguid;

    @UpdateTimestamp
    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;
}
