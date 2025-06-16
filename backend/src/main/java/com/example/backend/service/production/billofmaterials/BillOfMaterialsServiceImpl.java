package com.example.backend.service.production.billofmaterials;

import com.example.backend.domain.model.production.billofmaterials.BillOfMaterials;
import com.example.backend.dto.production.billofmaterials.BillOfMaterialsDto;
import com.example.backend.mapper.production.billofmaterials.BillOfMaterialsMapper;
import com.example.backend.mapper.production.billofmaterials.BillOfMaterialsResolver;
import com.example.backend.repository.production.billofmaterials.BillOfMaterialsRepository;
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
public class BillOfMaterialsServiceImpl implements BillOfMaterialsService {

    private final BillOfMaterialsRepository repository;
    private final BillOfMaterialsMapper mapper;
    private final BillOfMaterialsResolver resolver;

    @Override
    @Caching(evict = {
            @CacheEvict(value = BILL_OF_MATERIALS_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_BILL_OF_MATERIALS, allEntries = true)
    })
    @Timed(value = "billOfMaterials.create")
    public BillOfMaterialsDto create(BillOfMaterialsDto dto) {
        BillOfMaterials entity = mapper.toEntity(dto, resolver);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Cacheable(value = BILL_OF_MATERIALS_GET_BY_ID, key = "#id")
    @Timed(value = "billOfMaterials.get-by-id")
    public BillOfMaterialsDto getById(Integer id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BillOfMaterials not found")));
    }

    @Override
    @Cacheable(value = BILL_OF_MATERIALS_GET_ALL, key = "'all'")
    @Timed(value = "billOfMaterials.get-all")
    public List<BillOfMaterialsDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Cacheable(value = SEARCH_BILL_OF_MATERIALS, key = "{#pageable.pageNumber, #pageable.pageSize, #pageable.sort}")
    @Timed(value = "billOfMaterials.get-paginated")
    public PagedResponse<BillOfMaterialsDto> getPaginated(Pageable pageable) {
        Page<BillOfMaterials> page = repository.findAll(pageable);
        List<BillOfMaterialsDto> content = page.stream().map(mapper::toDto).toList();
        return new PagedResponse<>(content, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isLast());
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = BILL_OF_MATERIALS_GET_BY_ID, key = "#id"),
            @CacheEvict(value = BILL_OF_MATERIALS_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_BILL_OF_MATERIALS, allEntries = true)
    })
    @Timed(value = "billOfMaterials.update")
    public BillOfMaterialsDto update(Integer id, BillOfMaterialsDto dto) {
        BillOfMaterials entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BillOfMaterials not found"));
        mapper.updateEntityFromDto(dto, entity, resolver);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = BILL_OF_MATERIALS_GET_BY_ID, key = "#id"),
            @CacheEvict(value = BILL_OF_MATERIALS_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_BILL_OF_MATERIALS, allEntries = true)
    })
    @Timed(value = "billOfMaterials.delete")
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("BillOfMaterials not found");
        }
        repository.deleteById(id);
    }
}

