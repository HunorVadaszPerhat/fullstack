package com.example.backend.domain.model.person.person;

import com.example.backend.domain.model.person.businessentity.BusinessEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Human beings involved with AdventureWorks: employees, customer contacts, and vendor contacts.
 */
@Entity
@Table(name = "Person", schema = "Person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    @Id
    @Column(name = "BusinessEntityID", nullable = false)
    private Integer businessEntityId;

    @Enumerated(EnumType.STRING)
    @Column(name = "PersonType", nullable = false, length = 2)
    private PersonType personType;

    @Column(name = "NameStyle", nullable = false)
    private Boolean nameStyle = false;

    @Column(name = "Title", length = 8)
    private String title;

    @Column(name = "FirstName", nullable = false, length = 50)
    private String firstName;

    @Column(name = "MiddleName", length = 50)
    private String middleName;

    @Column(name = "LastName", nullable = false, length = 50)
    private String lastName;

    @Column(name = "Suffix", length = 10)
    private String suffix;

    @Column(name = "EmailPromotion", nullable = false)
    private Integer emailPromotion = 0;

    @Lob
    @Column(name = "AdditionalContactInfo")
    private String additionalContactInfo;

    @Lob
    @Column(name = "Demographics")
    private String demographics;

    @Column(name = "rowguid", nullable = false)
    private UUID rowguid;

    @UpdateTimestamp
    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;

    @OneToOne
    @MapsId
    @JoinColumn(name = "BusinessEntityID", referencedColumnName = "BusinessEntityID")
    private BusinessEntity businessEntity;
}
