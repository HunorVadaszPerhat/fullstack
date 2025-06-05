package com.example.backend.domain.repository.person.person;

import com.example.backend.domain.model.person.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}