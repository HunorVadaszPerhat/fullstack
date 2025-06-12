package com.example.backend.service.person.person;

import com.example.backend.dto.person.person.PersonDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PersonService {
    PersonDto create(PersonDto dto);
    PersonDto getById(Integer id);
    List<PersonDto> getAll();
    PagedResponse<PersonDto> getPaginated(Pageable pageable);
    PersonDto update(Integer id, PersonDto dto);
    void delete(Integer id);
}
