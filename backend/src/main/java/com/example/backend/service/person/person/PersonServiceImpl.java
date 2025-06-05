package com.example.backend.service.person.person;

import com.example.backend.domain.model.person.businessentity.BusinessEntity;
import com.example.backend.domain.model.person.person.Person;
import com.example.backend.domain.repository.person.businessentity.BusinessEntityRepository;
import com.example.backend.domain.repository.person.person.PersonRepository;
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

    /*
    * A Person must be associated with an existing BusinessEntity
    * - you must find BusinessEntity first as the primary key of Person
    * - businessEntityId is passed into DTO provided by client/request etc.
    * - when BusinessEntity is set on Person, ID is automatically used for Person due to @MapsId
    * */
    @Override
    public PersonDto create(PersonDto dto) {
        // Step 1: Find existing BusinessEntity
        BusinessEntity businessEntity = businessEntityRepository.findById(dto.getBusinessEntityId())
                .orElseThrow(() -> new EntityNotFoundException("BusinessEntity not found"));
        // Step 2: Map DTO to Person entity
        Person person = personMapper.toEntity(dto);
        // Step 3: Link Person to BusinessEntity
        person.setBusinessEntity(businessEntity);
        // Step 4: Save and return
        Person saved = personRepository.save(person);
        return personMapper.toDto(saved);
    }

    @Override
    public PersonDto getById(Integer id) {
        // Step 1: Look up the Person by ID; throw an exception if not found
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));

        // Step 2: Convert the found Person entity to a DTO and return it
        return personMapper.toDto(person);
    }

    @Override
    public List<PersonDto> getAll() {
        // Step 1: Retrieve all Person entities from the database
        return personRepository.findAll()
                .stream()
                // Step 2: Convert each Person entity to a PersonDto using the mapper
                .map(personMapper::toDto)
                // Step 3: Collect the result into a List and return
                .toList();
    }

    @Override
    public PersonDto update(Integer id, PersonDto dto) {
        // Step 1: Find the existing Person by ID (or throw if not found)
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));

        // Step 2: Map updated values from the DTO into the existing Person entity
        personMapper.updateFromDto(dto, person);

        // Step 3: If DTO includes a new BusinessEntity ID, fetch and update the reference
        if (dto.getBusinessEntityId() != null) {
            BusinessEntity be = businessEntityRepository.findById(dto.getBusinessEntityId())
                    .orElseThrow(() -> new EntityNotFoundException("BusinessEntity not found"));
            person.setBusinessEntity(be);
        }

        // Step 4: Save the updated Person and return the mapped DTO
        return personMapper.toDto(personRepository.save(person));
    }

    @Override
    public void delete(Integer id) {
        // Step 1: Check if a Person with the given ID exists; if not, throw an exception
        if (!personRepository.existsById(id)) {
            throw new EntityNotFoundException("Person not found");
        }

        // Step 2: Delete the Person entity by ID
        personRepository.deleteById(id);
    }
}
