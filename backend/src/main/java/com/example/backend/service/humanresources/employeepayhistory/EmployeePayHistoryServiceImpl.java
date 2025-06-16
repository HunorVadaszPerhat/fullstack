package com.example.backend.service.humanresources.employeepayhistory;

import com.example.backend.domain.model.humanresources.employeepayhistory.EmployeePayHistory;
import com.example.backend.domain.model.humanresources.employeepayhistory.EmployeePayHistoryId;
import com.example.backend.dto.humanresources.employeepayhistory.EmployeePayHistoryDto;
import com.example.backend.mapper.humanresources.employeepayhistory.EmployeePayHistoryMapper;
import com.example.backend.repository.humanresources.employeepayhistory.EmployeePayHistoryRepository;
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
public class EmployeePayHistoryServiceImpl implements EmployeePayHistoryService {

    private final EmployeePayHistoryRepository repository;
    private final EmployeePayHistoryMapper mapper;

    @Override
    @Cacheable(value = EMPLOYEE_PAY_HISTORIES_GET_ALL, key = "'all'")
    @Timed(value = "employeePayHistory.get-all", description = "Get all pay histories")
    public List<EmployeePayHistoryDto> getAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    @Cacheable(value = SEARCH_EMPLOYEE_PAY_HISTORIES, key = "{#pageable.pageNumber, #pageable.pageSize, #pageable.sort}")
    @Timed(value = "employeePayHistory.get-paginated", description = "Paginated pay histories")
    public PagedResponse<EmployeePayHistoryDto> getPaginated(Pageable pageable) {
        Page<EmployeePayHistory> page = repository.findAll(pageable);
        return new PagedResponse<>(
                page.getContent().stream().map(mapper::toDto).toList(),
                page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isLast()
        );
    }

    @Override
    @Cacheable(value = EMPLOYEE_PAY_HISTORIES_GET_BY_ID, key = "#id.businessEntityId + '-' + #id.rateChangeDate")
    @Timed(value = "employeePayHistory.get-by-id", description = "Get pay history by ID")
    public EmployeePayHistoryDto getById(EmployeePayHistoryId id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PayHistory not found: " + id)));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = EMPLOYEE_PAY_HISTORIES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_EMPLOYEE_PAY_HISTORIES, allEntries = true)
    })
    @Timed(value = "employeePayHistory.create", description = "Create pay history")
    public EmployeePayHistoryDto create(EmployeePayHistoryDto dto) {
        EmployeePayHistory entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = EMPLOYEE_PAY_HISTORIES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_EMPLOYEE_PAY_HISTORIES, allEntries = true),
            @CacheEvict(value = EMPLOYEE_PAY_HISTORIES_GET_BY_ID, key = "#id.businessEntityId + '-' + #id.rateChangeDate")
    })
    @Timed(value = "employeePayHistory.update", description = "Update pay history")
    public EmployeePayHistoryDto update(EmployeePayHistoryId id, EmployeePayHistoryDto dto) {
        EmployeePayHistory entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PayHistory not found: " + id));
        mapper.updateEntityFromDto(dto, entity);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = EMPLOYEE_PAY_HISTORIES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_EMPLOYEE_PAY_HISTORIES, allEntries = true),
            @CacheEvict(value = EMPLOYEE_PAY_HISTORIES_GET_BY_ID, key = "#id.businessEntityId + '-' + #id.rateChangeDate")
    })
    @Timed(value = "employeePayHistory.delete", description = "Delete pay history")
    public void delete(EmployeePayHistoryId id) {
        EmployeePayHistory entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PayHistory not found: " + id));
        repository.delete(entity);
    }
}

