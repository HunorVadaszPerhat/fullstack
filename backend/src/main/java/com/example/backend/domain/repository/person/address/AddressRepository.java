package com.example.backend.domain.repository.person.address;

import com.example.backend.domain.model.person.address.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AddressRepository extends JpaRepository<Address, Integer>, JpaSpecificationExecutor<Address> {
    Page<Address> findAll(Pageable pageable);

    // This method will generate a SQL query like:
    // SELECT * FROM addresses WHERE LOWER(city) LIKE LOWER('%city%')
    Page<Address> findByCityContainingIgnoreCase(String city, Pageable pageable);





}