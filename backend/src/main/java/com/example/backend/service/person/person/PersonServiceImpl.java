package com.example.backend.service.person.person;

import com.example.backend.domain.model.person.businessentity.BusinessEntity;
import com.example.backend.domain.model.person.person.Person;
import com.example.backend.domain.repository.person.BusinessEntityRepository;
import com.example.backend.domain.repository.person.PersonRepository;
import com.example.backend.dto.person.person.PersonDto;
import com.example.backend.mapper.person.person.PersonMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional // @Transactional ensures data consistency
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final BusinessEntityRepository businessEntityRepository;
    private final PersonMapper personMapper;

    @Override
    public PersonDto create(PersonDto dto) {
        // Ensure the BusinessEntity exists
        BusinessEntity businessEntity = businessEntityRepository.findById(dto.getBusinessEntityId())
                .orElseThrow(() -> new EntityNotFoundException("BusinessEntity not found"));

        Person person = personMapper.toEntity(dto);
        person.setBusinessEntity(businessEntity);

        Person saved = personRepository.save(person);
        return personMapper.toDto(saved);
    }

    @Override
    public PersonDto getById(Integer id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));
        return personMapper.toDto(person);
    }

    @Override
    public List<PersonDto> getAll() {
        return personRepository.findAll()
                .stream()
                .map(personMapper::toDto)
                .toList();
    }

    @Override
    public PersonDto update(Integer id, PersonDto dto) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));

        personMapper.updateFromDto(dto, person);

        if (dto.getBusinessEntityId() != null) {
            BusinessEntity be = businessEntityRepository.findById(dto.getBusinessEntityId())
                    .orElseThrow(() -> new EntityNotFoundException("BusinessEntity not found"));
            person.setBusinessEntity(be);
        }

        return personMapper.toDto(personRepository.save(person));
    }

    @Override
    public void delete(Integer id) {
        if (!personRepository.existsById(id)) {
            throw new EntityNotFoundException("Person not found");
        }
        personRepository.deleteById(id);
    }
}
