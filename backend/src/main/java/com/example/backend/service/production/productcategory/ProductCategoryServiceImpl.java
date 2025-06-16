package com.example.backend.service.production.productcategory;

import com.example.backend.constants.CacheNames;
import com.example.backend.domain.model.production.productcategory.ProductCategory;
import com.example.backend.dto.production.productcategory.ProductCategoryDto;
import com.example.backend.mapper.production.productcategory.ProductCategoryMapper;
import com.example.backend.repository.production.productcategory.ProductCategoryRepository;
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
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository repository;
    private final ProductCategoryMapper mapper;

    @Override
    @Cacheable(value = PRODUCT_CATEGORIES_GET_ALL)
    @Timed(value = "productCategories.get-all", description = "Time taken to get all product categories")
    public List<ProductCategoryDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Cacheable(value = SEARCH_PRODUCT_CATEGORIES, key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    @Timed(value = "productCategories.get-paginated", description = "Time taken to get paginated product categories")
    public PagedResponse<ProductCategoryDto> getPaginated(Pageable pageable) {
        Page<ProductCategory> page = repository.findAll(pageable);
        return new PagedResponse<>(
                page.getContent().stream().map(mapper::toDto).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    @Override
    @Cacheable(value = PRODUCT_CATEGORIES_GET_BY_ID, key = "#id")
    @Timed(value = "productCategories.get-by-id", description = "Time taken to get product category by ID")
    public ProductCategoryDto getById(Integer id) {
        ProductCategory entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductCategory not found with ID: " + id));
        return mapper.toDto(entity);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PRODUCT_CATEGORIES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PRODUCT_CATEGORIES, allEntries = true)
    })
    public ProductCategoryDto create(ProductCategoryDto dto) {
        ProductCategory entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PRODUCT_CATEGORIES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PRODUCT_CATEGORIES, allEntries = true),
            @CacheEvict(value = PRODUCT_CATEGORIES_GET_BY_ID, key = "#id")
    })
    public ProductCategoryDto update(Integer id, ProductCategoryDto dto) {
        ProductCategory entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductCategory not found with ID: " + id));
        mapper.updateEntityFromDto(dto, entity);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PRODUCT_CATEGORIES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PRODUCT_CATEGORIES, allEntries = true),
            @CacheEvict(value = PRODUCT_CATEGORIES_GET_BY_ID, key = "#id")
    })
    public void delete(Integer id) {
        ProductCategory entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductCategory not found with ID: " + id));
        repository.delete(entity);
    }
}
