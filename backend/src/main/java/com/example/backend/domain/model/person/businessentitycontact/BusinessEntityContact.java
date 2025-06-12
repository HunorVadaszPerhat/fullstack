package com.example.backend.domain.model.person.businessentitycontact;

import com.example.backend.domain.model.person.businessentity.BusinessEntity;
import com.example.backend.domain.model.person.contacttype.ContactType;
import com.example.backend.domain.model.person.person.Person;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "BusinessEntityContact", schema = "Person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessEntityContact {

    @EmbeddedId
    private BusinessEntityContactId id;

    @ManyToOne
    @MapsId("businessEntityId")
    @JoinColumn(name = "BusinessEntityID", nullable = false)
    private BusinessEntity businessEntity;

    @ManyToOne
    @MapsId("personId")
    @JoinColumn(name = "PersonID", nullable = false)
    private Person person;

    @ManyToOne
    @MapsId("contactTypeId")
    @JoinColumn(name = "ContactTypeID", nullable = false)
    private ContactType contactType;

    @Column(name = "rowguid", nullable = false)
    private UUID rowguid;

    @UpdateTimestamp
    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;
}

