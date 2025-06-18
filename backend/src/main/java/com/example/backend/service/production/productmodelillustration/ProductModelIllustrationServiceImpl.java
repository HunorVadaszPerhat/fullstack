package com.example.backend.service.production.productmodelillustration;

import com.example.backend.domain.model.production.productmodelillustration.ProductModelIllustration;
import com.example.backend.domain.model.production.productmodelillustration.ProductModelIllustrationId;
import com.example.backend.dto.production.productmodelillustration.ProductModelIllustrationDto;
import com.example.backend.mapper.production.productmodelillustration.ProductModelIllustrationMapper;
import com.example.backend.repository.production.productmodelillustration.ProductModelIllustrationRepository;
import com.example.backend.util.response.PagedResponse;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
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
public class ProductModelIllustrationServiceImpl implements ProductModelIllustrationService {

    private final ProductModelIllustrationRepository repository;
    private final ProductModelIllustrationMapper mapper;

    @Override
    @Cacheable(value = PRODUCT_MODEL_ILLUSTRATIONS_GET_ALL)
    @Timed(value = "productModelIllustration.get-all", description = "Time to get all mappings")
    public List<ProductModelIllustrationDto> getAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    @Cacheable(value = SEARCH_PRODUCT_MODEL_ILLUSTRATIONS, key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public PagedResponse<ProductModelIllustrationDto> getPaginated(Pageable pageable) {
        Page<ProductModelIllustration> page = repository.findAll(pageable);
        return new PagedResponse<>(
                page.getContent().stream().map(mapper::toDto).toList(),
                page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isLast()
        );
    }

    @Override
    @Cacheable(value = PRODUCT_MODEL_ILLUSTRATIONS_GET_BY_ID, key = "#productModelId + '-' + #illustrationId")
    public ProductModelIllustrationDto getById(Integer productModelId, Integer illustrationId) {
        var id = new ProductModelIllustrationId(productModelId, illustrationId);
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Mapping not found"));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PRODUCT_MODEL_ILLUSTRATIONS_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PRODUCT_MODEL_ILLUSTRATIONS, allEntries = true)
    })
    public ProductModelIllustrationDto create(ProductModelIllustrationDto dto) {
        var entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PRODUCT_MODEL_ILLUSTRATIONS_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PRODUCT_MODEL_ILLUSTRATIONS, allEntries = true),
            @CacheEvict(value = PRODUCT_MODEL_ILLUSTRATIONS_GET_BY_ID, key = "#productModelId + '-' + #illustrationId")
    })
    public ProductModelIllustrationDto update(Integer productModelId, Integer illustrationId, ProductModelIllustrationDto dto) {
        var id = new ProductModelIllustrationId(productModelId, illustrationId);
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mapping not found"));
        mapper.updateEntityFromDto(dto, entity);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PRODUCT_MODEL_ILLUSTRATIONS_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PRODUCT_MODEL_ILLUSTRATIONS, allEntries = true),
            @CacheEvict(value = PRODUCT_MODEL_ILLUSTRATIONS_GET_BY_ID, key = "#productModelId + '-' + #illustrationId")
    })
    public void delete(Integer productModelId, Integer illustrationId) {
        var id = new ProductModelIllustrationId(productModelId, illustrationId);
        repository.deleteById(id);
    }
}

