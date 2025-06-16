package com.example.backend.service.production.productcosthistory;

import com.example.backend.constants.CacheNames;
import com.example.backend.domain.model.production.product.Product;
import com.example.backend.domain.model.production.productcosthistory.ProductCostHistory;
import com.example.backend.domain.model.production.productcosthistory.ProductCostHistoryId;
import com.example.backend.dto.production.productcosthistory.ProductCostHistoryDto;
import com.example.backend.mapper.production.productcosthistory.ProductCostHistoryMapper;
import com.example.backend.mapper.production.productcosthistory.ProductCostHistoryResolver;
import com.example.backend.repository.production.productcosthistory.ProductCostHistoryRepository;
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

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductCostHistoryServiceImpl implements ProductCostHistoryService {

    private final ProductCostHistoryRepository repository;
    private final ProductCostHistoryMapper mapper;
    private final ProductCostHistoryResolver resolver;

    @Override
    @Cacheable(value = CacheNames.PRODUCT_COST_HISTORY_GET_ALL)
    @Timed(value = "productCostHistory.get-all", description = "Time taken to get all product cost history")
    public List<ProductCostHistoryDto> getAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    @Cacheable(value = CacheNames.SEARCH_PRODUCT_COST_HISTORY, key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    @Timed(value = "productCostHistory.get-paginated", description = "Time taken to get paginated product cost history")
    public PagedResponse<ProductCostHistoryDto> getPaginated(Pageable pageable) {
        Page<ProductCostHistory> page = repository.findAll(pageable);
        return new PagedResponse<>(
                page.getContent().stream().map(mapper::toDto).toList(),
                page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isLast()
        );
    }

    @Override
    @Cacheable(value = CacheNames.PRODUCT_COST_HISTORY_GET_BY_ID, key = "#productId + '-' + #startDate")
    @Timed(value = "productCostHistory.get-by-id", description = "Time taken to get product cost history by ID")
    public ProductCostHistoryDto getById(Integer productId, LocalDateTime startDate) {
        ProductCostHistoryId id = new ProductCostHistoryId(productId, startDate);
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("ProductCostHistory not found"));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheNames.PRODUCT_COST_HISTORY_GET_ALL, allEntries = true),
            @CacheEvict(value = CacheNames.SEARCH_PRODUCT_COST_HISTORY, allEntries = true)
    })
    public ProductCostHistoryDto create(ProductCostHistoryDto dto) {
        Product product = resolver.resolveProduct(dto.getProductId());
        ProductCostHistory entity = mapper.toEntity(dto, product);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheNames.PRODUCT_COST_HISTORY_GET_ALL, allEntries = true),
            @CacheEvict(value = CacheNames.SEARCH_PRODUCT_COST_HISTORY, allEntries = true),
            @CacheEvict(value = CacheNames.PRODUCT_COST_HISTORY_GET_BY_ID, key = "#productId + '-' + #startDate")
    })
    public ProductCostHistoryDto update(Integer productId, LocalDateTime startDate, ProductCostHistoryDto dto) {
        ProductCostHistoryId id = new ProductCostHistoryId(productId, startDate);
        ProductCostHistory entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductCostHistory not found"));
        mapper.updateEntityFromDto(dto, entity);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheNames.PRODUCT_COST_HISTORY_GET_ALL, allEntries = true),
            @CacheEvict(value = CacheNames.SEARCH_PRODUCT_COST_HISTORY, allEntries = true),
            @CacheEvict(value = CacheNames.PRODUCT_COST_HISTORY_GET_BY_ID, key = "#productId + '-' + #startDate")
    })
    public void delete(Integer productId, LocalDateTime startDate) {
        ProductCostHistoryId id = new ProductCostHistoryId(productId, startDate);
        ProductCostHistory entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductCostHistory not found"));
        repository.delete(entity);
    }
}

