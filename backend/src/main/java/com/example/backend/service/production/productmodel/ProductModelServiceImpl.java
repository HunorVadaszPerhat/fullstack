package com.example.backend.service.production.productmodel;

import com.example.backend.domain.model.production.productmodel.ProductModel;
import com.example.backend.dto.production.productmodel.ProductModelDto;
import com.example.backend.mapper.production.productmodel.ProductModelMapper;
import com.example.backend.repository.production.productmodel.ProductModelRepository;
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
public class ProductModelServiceImpl implements ProductModelService {

    private final ProductModelRepository repository;
    private final ProductModelMapper mapper;

    @Override
    @Cacheable(value = PRODUCT_MODELS_GET_ALL)
    @Timed(value = "productModels.get-all", description = "Time taken to get all product models")
    public List<ProductModelDto> getAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    @Cacheable(value = SEARCH_PRODUCT_MODELS, key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    @Timed(value = "productModels.get-paginated", description = "Time taken to get paginated productModels")
    public PagedResponse<ProductModelDto> getPaginated(Pageable pageable) {
        Page<ProductModel> page = repository.findAll(pageable);
        return new PagedResponse<>(
                page.getContent().stream().map(mapper::toDto).toList(),
                page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isLast()
        );
    }

    @Override
    @Cacheable(value = PRODUCT_MODELS_GET_BY_ID, key = "#id")
    @Timed(value = "productModels.get-by-id", description = "Time taken to get productModel by ID")
    public ProductModelDto getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("ProductModel not found"));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PRODUCT_MODELS_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PRODUCT_MODELS, allEntries = true),
            @CacheEvict(value = ADDRESSES_GET_BY_ID, key = "#dto.productModelId", condition = "#result != null"),
    })
    @Timed(value = "productModels.create", description = "Time taken to create productModel")
    public ProductModelDto create(ProductModelDto dto) {
        ProductModel entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PRODUCT_MODELS_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PRODUCT_MODELS, allEntries = true),
            @CacheEvict(value = PRODUCT_MODELS_GET_BY_ID, key = "#id")
    })
    @Timed(value = "productModels.update", description = "Time taken to update productModel")
    public ProductModelDto update(Integer id, ProductModelDto dto) {
        ProductModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductModel not found"));
        mapper.updateEntityFromDto(dto, entity);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PRODUCT_MODELS_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PRODUCT_MODELS, allEntries = true),
            @CacheEvict(value = PRODUCT_MODELS_GET_BY_ID, key = "#id")
    })
    @Timed(value = "productModels.delete", description = "Time taken to delete productModel")
    public void delete(Integer id) {
        ProductModel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductModel not found"));
        repository.delete(entity);
    }
}

