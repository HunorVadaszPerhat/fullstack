package com.example.backend.mapper.person.businessentityaddress;

import com.example.backend.domain.model.person.address.Address;
import com.example.backend.domain.model.person.addresstype.AddressType;
import com.example.backend.domain.model.person.businessentity.BusinessEntity;
import com.example.backend.domain.repository.person.address.AddressRepository;
import com.example.backend.domain.repository.person.addresstype.AddressTypeRepository;
import com.example.backend.domain.repository.person.businessentity.BusinessEntityRepository;
import org.springframework.stereotype.Component;

@Component
public class EntityResolver {

    private final BusinessEntityRepository businessEntityRepository;
    private final AddressRepository addressRepository;
    private final AddressTypeRepository addressTypeRepository;

    public EntityResolver(BusinessEntityRepository businessEntityRepository,
                          AddressRepository addressRepository,
                          AddressTypeRepository addressTypeRepository) {
        this.businessEntityRepository = businessEntityRepository;
        this.addressRepository = addressRepository;
        this.addressTypeRepository = addressTypeRepository;
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
}

