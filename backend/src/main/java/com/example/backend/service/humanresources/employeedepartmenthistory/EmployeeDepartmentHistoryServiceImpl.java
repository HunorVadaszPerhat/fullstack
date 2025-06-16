package com.example.backend.service.humanresources.employeedepartmenthistory;

import com.example.backend.domain.model.humanresources.employeedepartmenthistory.EmployeeDepartmentHistory;
import com.example.backend.domain.model.humanresources.employeedepartmenthistory.EmployeeDepartmentHistoryId;
import com.example.backend.dto.humanresources.employeedepartmenthistory.EmployeeDepartmentHistoryDto;
import com.example.backend.mapper.humanresources.employeedepartmenthistory.EmployeeDepartmentHistoryMapper;
import com.example.backend.mapper.humanresources.employeedepartmenthistory.EmployeeDepartmentHistoryResolver;
import com.example.backend.repository.humanresources.employeedepartmenthistory.EmployeeDepartmentHistoryRepository;
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
public class EmployeeDepartmentHistoryServiceImpl implements EmployeeDepartmentHistoryService {

    private final EmployeeDepartmentHistoryRepository repository;
    private final EmployeeDepartmentHistoryMapper mapper;
    private final EmployeeDepartmentHistoryResolver resolver;

    @Override
    @Cacheable(value = EMPLOYEE_DEPARTMENT_HISTORY_GET_BY_ID, key = "#id")
    @Timed(value = "employeeDeptHistory.get-by-id")
    public EmployeeDepartmentHistoryDto getById(EmployeeDepartmentHistoryId id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found: " + id)));
    }

    @Override
    @Cacheable(value = EMPLOYEE_DEPARTMENT_HISTORY_GET_ALL, key = "'all'")
    @Timed(value = "employeeDeptHistory.get-all")
    public List<EmployeeDepartmentHistoryDto> getAll() {
        return repository.findAll()
                .stream().map(mapper::toDto).toList();
    }

    @Override
    @Cacheable(value = SEARCH_EMPLOYEE_DEPARTMENT_HISTORY, key = "{#pageable.pageNumber, #pageable.pageSize, #pageable.sort}")
    @Timed(value = "employeeDeptHistory.get-paginated")
    public PagedResponse<EmployeeDepartmentHistoryDto> getPaginated(Pageable pageable) {
        Page<EmployeeDepartmentHistory> page = repository.findAll(pageable);
        return new PagedResponse<>(
                page.map(mapper::toDto).getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = EMPLOYEE_DEPARTMENT_HISTORY_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_EMPLOYEE_DEPARTMENT_HISTORY, allEntries = true)
    })
    @Timed(value = "employeeDeptHistory.create")
    public EmployeeDepartmentHistoryDto create(EmployeeDepartmentHistoryDto dto) {
        EmployeeDepartmentHistory entity = mapper.toEntity(dto, resolver);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = EMPLOYEE_DEPARTMENT_HISTORY_GET_BY_ID, key = "#id"),
            @CacheEvict(value = EMPLOYEE_DEPARTMENT_HISTORY_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_EMPLOYEE_DEPARTMENT_HISTORY, allEntries = true)
    })
    @Timed(value = "employeeDeptHistory.update")
    public EmployeeDepartmentHistoryDto update(EmployeeDepartmentHistoryId id, EmployeeDepartmentHistoryDto dto) {
        EmployeeDepartmentHistory entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found: " + id));
        mapper.updateEntityFromDto(dto, entity, resolver);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = EMPLOYEE_DEPARTMENT_HISTORY_GET_BY_ID, key = "#id"),
            @CacheEvict(value = EMPLOYEE_DEPARTMENT_HISTORY_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_EMPLOYEE_DEPARTMENT_HISTORY, allEntries = true)
    })
    @Timed(value = "employeeDeptHistory.delete")
    public void delete(EmployeeDepartmentHistoryId id) {
        repository.deleteById(id);
    }
}
