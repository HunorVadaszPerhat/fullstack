package com.example.backend.domain.repository.person.address;

import com.example.backend.domain.model.person.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}