package com.example.backend.dto.person.person;

import com.example.backend.domain.model.person.person.PersonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDto {
    // private Integer businessEntityID;
    private Integer businessEntityId;  // for mapping the FK

    private PersonType personType;
    private Boolean nameStyle;
    private String title;
    private String firstName;
    private String middleName;
    private String lastName;
    private String suffix;
    private Integer emailPromotion;
    private String additionalContactInfo;
    private String demographics;
    private UUID rowguid;
    private Date modifiedDate;
}
