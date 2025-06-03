package com.example.backend.service.person.person;

import com.example.backend.dto.person.person.PersonDto;

import java.util.List;

public interface PersonService {
    PersonDto create(PersonDto dto);
    PersonDto getById(Integer id);
    List<PersonDto> getAll();
    PersonDto update(Integer id, PersonDto dto);
    void delete(Integer id);
}
