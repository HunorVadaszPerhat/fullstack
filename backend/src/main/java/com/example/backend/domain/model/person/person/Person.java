package com.example.backend.domain.model.person.person;

import com.example.backend.domain.model.person.businessentity.BusinessEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Person", schema = "Person")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {
    @Id
    @Column(name = "BusinessEntityID", nullable = false)
    @NotNull(message = "Business entity ID is required")
    private Integer businessEntityID;

    @OneToOne
    @MapsId
    @JoinColumn(name = "BusinessEntityID", referencedColumnName = "BusinessEntityID")
    private BusinessEntity businessEntity;

    @NotNull(message = "Person type is required")
    @Column(name = "PersonType", columnDefinition = "nchar(2)", nullable = false)
    @Enumerated(EnumType.STRING)
    private PersonType personType;

    @NotNull(message = "Name style is required")
    @Column(name = "NameStyle", nullable = false)
    private Boolean nameStyle;

    @Size(max = 8, message = "Title must be at most 8 characters")
    @Column(name = "Title", length = 8)
    private String title;

    @NotBlank(message = "First name is required")
    @Size(max = 50)
    @Column(name = "FirstName", nullable = false, length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "MiddleName", length = 50)
    private String middleName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50)
    @Column(name = "LastName", nullable = false, length = 50)
    private String lastName;

    @Size(max = 10)
    @Column(name = "Suffix", length = 10)
    private String suffix;

    @NotNull(message = "Email promotion value is required")
    @Min(0)
    @Max(2)
    @Column(name = "EmailPromotion", nullable = false)
    private Integer emailPromotion;

    @Lob
    @Column(name = "AdditionalContactInfo", columnDefinition = "xml")
    private String additionalContactInfo;

    @Lob
    @Column(name = "Demographics", columnDefinition = "xml")
    private String demographics;

    @Column(name = "rowguid", nullable = false, columnDefinition = "uniqueidentifier default newid()")
    private UUID rowguid;

    @Column(name = "ModifiedDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
}
