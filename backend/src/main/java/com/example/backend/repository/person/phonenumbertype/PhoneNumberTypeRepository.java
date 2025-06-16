package com.example.backend.repository.person.phonenumbertype;

import com.example.backend.domain.model.person.phonenumbertype.PhoneNumberType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneNumberTypeRepository extends JpaRepository<PhoneNumberType, Integer> {
}

