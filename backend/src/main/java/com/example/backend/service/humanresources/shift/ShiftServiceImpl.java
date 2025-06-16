package com.example.backend.service.humanresources.shift;

import com.example.backend.domain.model.humanresources.shift.Shift;
import com.example.backend.dto.humanresources.shift.ShiftDto;
import com.example.backend.mapper.humanresources.shift.ShiftMapper;
import com.example.backend.repository.humanresources.shift.ShiftRepository;
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

import java.util.List;

import static com.example.backend.constants.CacheNames.*;

@Service
@RequiredArgsConstructor
public class ShiftServiceImpl implements ShiftService {

    private final ShiftRepository repository;
    private final ShiftMapper mapper;

    @Override
    @Cacheable(value = SHIFTS_GET_BY_ID, key = "#id")
    @Timed(value = "shift.get-by-id", description = "Time to fetch shift by ID")
    public ShiftDto getById(Byte id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Shift not found: " + id)));
    }

    @Override
    @Cacheable(value = SHIFTS_GET_ALL, key = "'all'")
    @Timed(value = "shift.get-all", description = "Time to fetch all shifts")
    public List<ShiftDto> getAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    @Cacheable(value = SEARCH_SHIFTS, key = "{#pageable.pageNumber, #pageable.pageSize, #pageable.sort}")
    @Timed(value = "shift.get-paginated", description = "Time to fetch paginated shifts")
    public PagedResponse<ShiftDto> getPaginated(Pageable pageable) {
        Page<Shift> page = repository.findAll(pageable);
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
            @CacheEvict(value = SHIFTS_GET_ALL, allEntries = true),
            @CacheEvict(value = SHIFTS_GET_BY_ID, key = "#result.shiftId", condition = "#result != null"),
            @CacheEvict(value = SEARCH_SHIFTS, allEntries = true)
    })
    @Timed(value = "shift.create", description = "Time to create shift")
    public ShiftDto create(ShiftDto dto) {
        Shift saved = repository.save(mapper.toEntity(dto));
        return mapper.toDto(saved);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = SHIFTS_GET_ALL, allEntries = true),
            @CacheEvict(value = SHIFTS_GET_BY_ID, key = "#id"),
            @CacheEvict(value = SEARCH_SHIFTS, allEntries = true)
    })
    @Timed(value = "shift.update", description = "Time to update shift")
    public ShiftDto update(Byte id, ShiftDto dto) {
        Shift existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Shift not found: " + id));
        mapper.updateEntityFromDto(dto, existing);
        return mapper.toDto(repository.save(existing));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = SHIFTS_GET_ALL, allEntries = true),
            @CacheEvict(value = SHIFTS_GET_BY_ID, key = "#id"),
            @CacheEvict(value = SEARCH_SHIFTS, allEntries = true)
    })
    @Timed(value = "shift.delete", description = "Time to delete shift")
    public void delete(Byte id) {
        Shift shift = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Shift not found: " + id));
        repository.delete(shift);
    }
}
