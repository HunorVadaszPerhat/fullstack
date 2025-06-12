package com.example.backend.domain.repository.person.addresstype;

import com.example.backend.domain.model.person.addresstype.AddressType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressTypeRepository extends JpaRepository<AddressType, Integer> {
    Page<AddressType> findAll(Pageable pageable);
}
