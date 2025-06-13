package com.example.backend.service.humanresources.department;

import com.example.backend.domain.model.humanresources.department.Department;
import com.example.backend.repository.humanresources.department.DepartmentRepository;
import com.example.backend.dto.humanresources.department.DepartmentDto;
import com.example.backend.mapper.humanresources.department.DepartmentMapper;
import com.example.backend.util.response.PagedResponse;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

import static com.example.backend.constants.CacheNames.*;


@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository repository;
    private final DepartmentMapper mapper;

    @Override
    @Cacheable(value = DEPARTMENTS_GET_ALL, key = "'all'")
    @Timed(value = "department.get-all", description = "Time taken to get all departments")
    public List<DepartmentDto> getAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    @Cacheable(value = DEPARTMENTS_GET_BY_ID, key = "#id")
    @Timed(value = "department.get-by-id", description = "Time taken to get department by ID")
    public DepartmentDto getById(Short id) {
        Department entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found: " + id));
        return mapper.toDto(entity);
    }

    @Override
    @Cacheable(value = SEARCH_DEPARTMENTS, key = "{#pageable.pageNumber, #pageable.pageSize, #pageable.sort}")
    @Timed(value = "department.get-paginated", description = "Time taken to get paginated departments")
    public PagedResponse<DepartmentDto> getPaginated(Pageable pageable) {
        Page<Department> page = repository.findAll(pageable);
        List<DepartmentDto> content = page.getContent().stream()
                .map(mapper::toDto)
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
            @CacheEvict(value = DEPARTMENTS_GET_ALL, allEntries = true),
            @CacheEvict(value = DEPARTMENTS_GET_BY_ID, key = "#result.departmentId", condition = "#result != null")
    })
    @Timed(value = "department.create", description = "Time taken to create department")
    public DepartmentDto create(DepartmentDto dto) {
        Department saved = repository.save(mapper.toEntity(dto));
        return mapper.toDto(saved);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = DEPARTMENTS_GET_ALL, allEntries = true),
            @CacheEvict(value = DEPARTMENTS_GET_BY_ID, key = "#id")
    })
    @Timed(value = "department.update", description = "Time taken to update department")
    public DepartmentDto update(Short id, DepartmentDto dto) {
        Department existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found: " + id));
        mapper.updateFromDto(dto, existing);
        return mapper.toDto(repository.save(existing));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = DEPARTMENTS_GET_ALL, allEntries = true),
            @CacheEvict(value = DEPARTMENTS_GET_BY_ID, key = "#id")
    })
    @Timed(value = "department.delete", description = "Time taken to delete department")
    public void delete(Short id) {
        Department existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found: " + id));
        repository.delete(existing);
    }
}

