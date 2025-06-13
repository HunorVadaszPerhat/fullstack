package com.example.backend.mapper.person.businessentitycontact;

import com.example.backend.domain.model.person.address.Address;
import com.example.backend.domain.model.person.addresstype.AddressType;
import com.example.backend.domain.model.person.businessentity.BusinessEntity;
import com.example.backend.domain.model.person.contacttype.ContactType;
import com.example.backend.domain.model.person.person.Person;
import com.example.backend.repository.person.address.AddressRepository;
import com.example.backend.repository.person.addresstype.AddressTypeRepository;
import com.example.backend.repository.person.businessentity.BusinessEntityRepository;
import com.example.backend.repository.person.contacttype.ContactTypeRepository;
import com.example.backend.repository.person.person.PersonRepository;
import org.springframework.stereotype.Component;

@Component
public class BusinessEntityContactResolver {

    private final BusinessEntityRepository businessEntityRepository;
    private final AddressRepository addressRepository;
    private final AddressTypeRepository addressTypeRepository;
    private final ContactTypeRepository contactTypeRepository;
    private final PersonRepository personRepository;

    public BusinessEntityContactResolver(BusinessEntityRepository businessEntityRepository,
                                         AddressRepository addressRepository,
                                         AddressTypeRepository addressTypeRepository,
                                         ContactTypeRepository contactTypeRepository,
                                         PersonRepository personRepository) {
        this.businessEntityRepository = businessEntityRepository;
        this.addressRepository = addressRepository;
        this.addressTypeRepository = addressTypeRepository;
        this.contactTypeRepository = contactTypeRepository;
        this.personRepository = personRepository;
    }

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
}

