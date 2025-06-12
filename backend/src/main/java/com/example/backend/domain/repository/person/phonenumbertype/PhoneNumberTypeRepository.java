package com.example.backend.domain.repository.person.phonenumbertype;

import com.example.backend.domain.model.person.person.Person;
import com.example.backend.domain.model.person.phonenumbertype.PhoneNumberType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneNumberTypeRepository extends JpaRepository<PhoneNumberType, Integer> {
    Page<PhoneNumberType> findAll(Pageable pageable);
}

