package com.example.backend.repository.person.businessentityaddress;

import com.example.backend.domain.model.person.businessentityaddress.BusinessEntityAddress;
import com.example.backend.domain.model.person.businessentityaddress.BusinessEntityAddressId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessEntityAddressRepository extends JpaRepository<BusinessEntityAddress, BusinessEntityAddressId> {
}

