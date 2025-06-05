package com.example.backend.domain.model.person.person;

import com.example.backend.domain.model.person.businessentity.BusinessEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

/**
 * Human beings involved with AdventureWorks: employees, customer contacts, and vendor contacts.
 */
@Entity
@Table(
        name = "Person",
        schema = "Person",
        uniqueConstraints = @UniqueConstraint(name = "AK_Person_rowguid", columnNames = "rowguid"),
        indexes = {
                @Index(name = "IX_Person_LastName_FirstName_MiddleName", columnList = "LastName, FirstName, MiddleName"),
                @Index(name = "PXML_Person_AddContact", columnList = "AdditionalContactInfo"),
                @Index(name = "PXML_Person_Demographics", columnList = "Demographics"),
                @Index(name = "XMLPATH_Person_Demographics", columnList = "Demographics"),
                @Index(name = "XMLPROPERTY_Person_Demographics", columnList = "Demographics"),
                @Index(name = "XMLVALUE_Person_Demographics", columnList = "Demographics")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    /**
     * Primary key for Person records.
     * Foreign key to BusinessEntity.BusinessEntityID.
     */
    @Id
    @Column(name = "BusinessEntityID", nullable = false)
    private Integer businessEntityId;

    /**
     * Primary type of person: SC = Store Contact, IN = Individual (retail) customer,
     * SP = Sales person, EM = Employee (non-sales), VC = Vendor contact, GC = General contact
     */
    @Column(name = "PersonType", nullable = false, length = 2)
    private String personType;

    /**
     * 0 = The data in FirstName and LastName are stored in western style (first name, last name) order.
     * 1 = Eastern style (last name, first name) order.
     */
    @Column(name = "NameStyle", nullable = false)
    private Boolean nameStyle = false;

    /**
     * A courtesy title. For example, Mr. or Ms.
     */
    @Column(name = "Title", length = 8)
    private String title;

    /**
     * First name of the person.
     */
    @Column(name = "FirstName", nullable = false, length = 50)
    private String firstName;

    /**
     * Middle name or middle initial of the person.
     */
    @Column(name = "MiddleName", length = 50)
    private String middleName;

    /**
     * Last name of the person.
     */
    @Column(name = "LastName", nullable = false, length = 50)
    private String lastName;

    /**
     * Surname suffix. For example, Sr. or Jr.
     */
    @Column(name = "Suffix", length = 10)
    private String suffix;

    /**
     * 0 = No email promotion, 1 = Receive promotions, 2 = Receive promotions from partners.
     */
    @Column(name = "EmailPromotion", nullable = false)
    private Integer emailPromotion = 0;

    /**
     * Additional contact information about the person stored in XML format.
     */
    @Lob
    @Column(name = "AdditionalContactInfo", columnDefinition = "xml")
    private String additionalContactInfo;

    /**
     * Personal information such as hobbies and income collected from online shoppers.
     */
    @Lob
    @Column(name = "Demographics", columnDefinition = "xml")
    private String demographics;

    /**
     * ROWGUIDCOL number uniquely identifying the record.
     * Used to support a merge replication sample.
     */
    @Column(name = "rowguid", nullable = false, columnDefinition = "uniqueidentifier default newid()", unique = true)
    private UUID rowguid = UUID.randomUUID();

    /**
     * Date and time the record was last updated.
     * Automatically set when the entity is first created.
     */
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ModifiedDate", nullable = false, columnDefinition = "datetime default getdate()")
    private Date modifiedDate;

    /**
     * Link to associated BusinessEntity.
     */
    @OneToOne
    @MapsId
    @JoinColumn(name = "BusinessEntityID", referencedColumnName = "BusinessEntityID")
    private BusinessEntity businessEntity;
}
