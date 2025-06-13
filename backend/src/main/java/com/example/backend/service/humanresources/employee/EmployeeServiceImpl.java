package com.example.backend.service.humanresources.employee;


import com.example.backend.domain.model.humanresources.employee.Employee;
import com.example.backend.domain.model.person.person.Person;
import com.example.backend.dto.humanresources.employee.EmployeeDto;
import com.example.backend.mapper.humanresources.employee.EmployeeMapper;
import com.example.backend.repository.humanresources.employee.EmployeeRepository;
import com.example.backend.repository.person.person.PersonRepository;
import com.example.backend.util.response.PagedResponse;
import io.micrometer.core.annotation.Timed;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.backend.constants.CacheNames.*;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PersonRepository personRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    @Cacheable(value = EMPLOYEES_GET_ALL, key = "'all'")
    @Timed(value = "employee.get-all", description = "Time taken to get all employees")
    public List<EmployeeDto> getAll() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    @Override
    @Cacheable(value = EMPLOYEES_GET_BY_ID, key = "#id")
    @Timed(value = "employee.get-by-id", description = "Time taken to get employee by ID")
    public EmployeeDto getById(Integer id) {
        Employee entity = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + id));
        return employeeMapper.toDto(entity);
    }

    @Override
    @Cacheable(value = SEARCH_EMPLOYEES, key = "{#pageable.pageNumber, #pageable.pageSize, #pageable.sort}")
    @Timed(value = "employee.get-paginated", description = "Time taken to get paginated employees")
    public PagedResponse<EmployeeDto> getPaginated(Pageable pageable) {
        Page<Employee> page = employeeRepository.findAll(pageable);
        List<EmployeeDto> content = page.getContent().stream()
                .map(employeeMapper::toDto)
                .toList();
        return new PagedResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = EMPLOYEES_GET_BY_ID, key = "#result.businessEntityId", condition = "#result != null"),
            @CacheEvict(value = EMPLOYEES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_EMPLOYEES, allEntries = true)
    })
    @Timed(value = "employee.create", description = "Time taken to create employee")
    public EmployeeDto create(EmployeeDto dto) {
        Person person = personRepository.findById(dto.getBusinessEntityId())
                .orElseThrow(() -> new EntityNotFoundException("Person not found with ID: " + dto.getBusinessEntityId()));

        Employee entity = employeeMapper.toEntity(dto);
        entity.setPerson(person);

        Employee saved = employeeRepository.save(entity);
        return employeeMapper.toDto(saved);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = EMPLOYEES_GET_BY_ID, key = "#id"),
            @CacheEvict(value = EMPLOYEES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_EMPLOYEES, allEntries = true)
    })
    @Timed(value = "employee.update", description = "Time taken to update employee")
    public EmployeeDto update(Integer id, EmployeeDto dto) {
        Employee entity = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + id));

        employeeMapper.updateEntityFromDto(dto, entity);
        Employee updated = employeeRepository.save(entity);
        return employeeMapper.toDto(updated);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = EMPLOYEES_GET_BY_ID, key = "#id"),
            @CacheEvict(value = EMPLOYEES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_EMPLOYEES, allEntries = true)
    })
    @Timed(value = "employee.delete", description = "Time taken to delete employee")
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Employees cannot be deleted. They can only be marked as not current.");
    }
}
