package com.example.backend.dto.person.emailaddress;

import com.example.backend.domain.model.person.emailaddress.EmailAddressId;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailAddressIdDto implements Serializable {
    private Integer businessEntityId;
    private Integer emailAddressId;

    public EmailAddressId toEntity() {
        return new EmailAddressId(businessEntityId, emailAddressId);
    }
}