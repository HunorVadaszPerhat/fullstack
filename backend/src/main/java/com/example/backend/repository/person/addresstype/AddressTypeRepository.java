package com.example.backend.repository.person.addresstype;

import com.example.backend.domain.model.person.addresstype.AddressType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressTypeRepository extends JpaRepository<AddressType, Integer> {
}
