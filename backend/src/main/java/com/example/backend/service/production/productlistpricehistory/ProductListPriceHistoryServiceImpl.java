package com.example.backend.service.production.productlistpricehistory;

import com.example.backend.domain.model.production.product.Product;
import com.example.backend.domain.model.production.productlistpricehistory.ProductListPriceHistory;
import com.example.backend.domain.model.production.productlistpricehistory.ProductListPriceHistoryId;
import com.example.backend.dto.production.productlistpricehistory.ProductListPriceHistoryDto;
import com.example.backend.mapper.production.productlistpricehistory.ProductListPriceHistoryMapper;
import com.example.backend.repository.production.product.ProductRepository;
import com.example.backend.repository.production.productlistpricehistory.ProductListPriceHistoryRepository;
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

import static com.example.backend.constants.CacheNames.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductListPriceHistoryServiceImpl implements ProductListPriceHistoryService {

    private final ProductListPriceHistoryRepository repository;
    private final ProductRepository productRepository;
    private final ProductListPriceHistoryMapper mapper;

    @Override
    @Cacheable(value = PRODUCT_LIST_PRICE_HISTORY_GET_ALL, key = "'all'")
    @Timed(value = "productListPriceHistory.get-all", description = "Get all list price history")
    public List<ProductListPriceHistoryDto> getAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    @Cacheable(value = SEARCH_PRODUCT_LIST_PRICE_HISTORY, key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    @Timed(value = "productListPriceHistory.get-paginated", description = "Time taken to get paginated product list price histories")
    public PagedResponse<ProductListPriceHistoryDto> getPaginated(Pageable pageable) {
        Page<ProductListPriceHistory> page = repository.findAll(pageable);
        return new PagedResponse<>(
                page.getContent().stream().map(mapper::toDto).toList(),
                page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isLast()
        );
    }

    @Override
    @Cacheable(value = PRODUCT_LIST_PRICE_HISTORY_GET_BY_ID, key = "#productId + '-' + #startDate")
    @Timed(value = "productListPriceHistory.get-by-id", description = "Time taken to get product list price history by ID")
    public ProductListPriceHistoryDto getById(Integer productId, LocalDateTime startDate) {
        ProductListPriceHistoryId id = new ProductListPriceHistoryId(productId, startDate);
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("List price history not found"));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PRODUCT_LIST_PRICE_HISTORY_GET_BY_ID, key = "#dto.productId", condition = "#result != null"),
            @CacheEvict(value = PRODUCT_LIST_PRICE_HISTORY_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PRODUCT_LIST_PRICE_HISTORY, allEntries = true)
    })
    @Timed(value = "productListPriceHistory.create", description = "Time taken to create product list price history")
    public ProductListPriceHistoryDto create(ProductListPriceHistoryDto dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        ProductListPriceHistory entity = mapper.toEntity(dto, product);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PRODUCT_LIST_PRICE_HISTORY_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PRODUCT_LIST_PRICE_HISTORY, allEntries = true),
            @CacheEvict(value = PRODUCT_LIST_PRICE_HISTORY_GET_BY_ID, key = "#productId + '-' + #startDate")
    })
    @Timed(value = "productListPriceHistory.update", description = "Time taken to update product list price history")
    public ProductListPriceHistoryDto update(Integer productId, LocalDateTime startDate, ProductListPriceHistoryDto dto) {
        ProductListPriceHistoryId id = new ProductListPriceHistoryId(productId, startDate);
        ProductListPriceHistory entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found"));
        mapper.updateEntityFromDto(dto, entity);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PRODUCT_LIST_PRICE_HISTORY_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PRODUCT_LIST_PRICE_HISTORY, allEntries = true),
            @CacheEvict(value = PRODUCT_LIST_PRICE_HISTORY_GET_BY_ID, key = "#productId + '-' + #startDate")
    })
    @Timed(value = "productListPriceHistory.delete", description = "Time taken to delete product list price history")
    public void delete(Integer productId, LocalDateTime startDate) {
        ProductListPriceHistoryId id = new ProductListPriceHistoryId(productId, startDate);
        repository.deleteById(id);
    }
}

