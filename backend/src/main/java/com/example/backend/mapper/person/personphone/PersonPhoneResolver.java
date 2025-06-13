package com.example.backend.mapper.person.personphone;

import com.example.backend.domain.model.person.address.Address;
import com.example.backend.domain.model.person.addresstype.AddressType;
import com.example.backend.domain.model.person.businessentity.BusinessEntity;
import com.example.backend.domain.model.person.contacttype.ContactType;
import com.example.backend.domain.model.person.person.Person;
import com.example.backend.domain.model.person.phonenumbertype.PhoneNumberType;
import com.example.backend.domain.repository.person.address.AddressRepository;
import com.example.backend.domain.repository.person.addresstype.AddressTypeRepository;
import com.example.backend.domain.repository.person.businessentity.BusinessEntityRepository;
import com.example.backend.domain.repository.person.contacttype.ContactTypeRepository;
import com.example.backend.domain.repository.person.person.PersonRepository;
import com.example.backend.domain.repository.person.phonenumbertype.PhoneNumberTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonPhoneResolver {

    private final BusinessEntityRepository businessEntityRepository;
    private final AddressRepository addressRepository;
    private final AddressTypeRepository addressTypeRepository;
    private final ContactTypeRepository contactTypeRepository;
    private final PersonRepository personRepository;
    private final PhoneNumberTypeRepository phoneNumberTypeRepository;

    public BusinessEntity resolveBusinessEntity(Integer id) {
        return businessEntityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BusinessEntity not found: " + id));
    }

    public Address resolveAddress(Integer id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found: " + id));
    }

    public AddressType resolveAddressType(Integer id) {
        return addressTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AddressType not found: " + id));
    }

    public ContactType resolveContactType(Integer id) {
        return contactTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ContactType not found: " + id));
    }

    public Person resolvePerson(Integer id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found: " + id));
    }

    public PhoneNumberType resolvePhoneNumberType(Integer id) {
        return phoneNumberTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PhoneNumberType not found: " + id));
    }
}

