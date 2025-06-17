package com.example.backend.service.production.productdescription;

import com.example.backend.domain.model.production.productdescription.ProductDescription;
import com.example.backend.dto.production.productdescription.ProductDescriptionDto;
import com.example.backend.mapper.production.productdescription.ProductDescriptionMapper;
import com.example.backend.repository.production.productdescription.ProductDescriptionRepository;
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
public class ProductDescriptionServiceImpl implements ProductDescriptionService {

    private final ProductDescriptionRepository repository;
    private final ProductDescriptionMapper mapper;

    @Override
    @Cacheable(value = PRODUCT_DESCRIPTIONS_GET_ALL)
    @Timed(value = "productDescription.get-all", description = "Time taken to get all product descriptions")
    public List<ProductDescriptionDto> getAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    @Cacheable(value = SEARCH_PRODUCT_DESCRIPTIONS, key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    @Timed(value = "productDescription.get-paginated", description = "Time taken to get paginated product descriptions")
    public PagedResponse<ProductDescriptionDto> getPaginated(Pageable pageable) {
        Page<ProductDescription> page = repository.findAll(pageable);
        return new PagedResponse<>(
                page.getContent().stream().map(mapper::toDto).toList(),
                page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isLast()
        );
    }

    @Override
    @Cacheable(value = PRODUCT_DESCRIPTIONS_GET_BY_ID, key = "#id")
    @Timed(value = "productDescription.get-by-id", description = "Time taken to get product description by ID")
    public ProductDescriptionDto getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("ProductDescription not found with id: " + id));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PRODUCT_DESCRIPTIONS_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PRODUCT_DESCRIPTIONS, allEntries = true)
    })
    public ProductDescriptionDto create(ProductDescriptionDto dto) {
        ProductDescription entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PRODUCT_DESCRIPTIONS_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PRODUCT_DESCRIPTIONS, allEntries = true),
            @CacheEvict(value = PRODUCT_DESCRIPTIONS_GET_BY_ID, key = "#id")
    })
    public ProductDescriptionDto update(Integer id, ProductDescriptionDto dto) {
        ProductDescription entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductDescription not found"));
        mapper.updateEntityFromDto(dto, entity);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PRODUCT_DESCRIPTIONS_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PRODUCT_DESCRIPTIONS, allEntries = true),
            @CacheEvict(value = PRODUCT_DESCRIPTIONS_GET_BY_ID, key = "#id")
    })
    public void delete(Integer id) {
        ProductDescription entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductDescription not found"));
        repository.delete(entity);
    }
}

