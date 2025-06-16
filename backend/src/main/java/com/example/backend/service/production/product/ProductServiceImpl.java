package com.example.backend.service.production.product;

import com.example.backend.domain.model.production.product.Product;
import com.example.backend.dto.production.product.ProductDto;
import com.example.backend.mapper.production.product.ProductMapper;
import com.example.backend.repository.production.product.ProductRepository;
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
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Override
    @Cacheable(value = PRODUCTS_GET_ALL)
    @Timed(value = "products.get-all", description = "Time taken to get all products")
    public List<ProductDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Cacheable(value = SEARCH_PRODUCTS, key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    @Timed(value = "products.get-paginated", description = "Time taken to get paginated products")
    public PagedResponse<ProductDto> getPaginated(Pageable pageable) {
        Page<Product> page = repository.findAll(pageable);
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
    @Cacheable(value = PRODUCTS_GET_BY_ID, key = "#id")
    @Timed(value = "products.get-by-id", description = "Time taken to get product by ID")
    public ProductDto getById(Integer id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
        return mapper.toDto(product);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PRODUCTS_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PRODUCTS, allEntries = true)
    })
    public ProductDto create(ProductDto dto) {
        Product entity = mapper.toEntity(dto);
        Product saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PRODUCTS_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PRODUCTS, allEntries = true),
            @CacheEvict(value = PRODUCTS_GET_BY_ID, key = "#id")
    })
    public ProductDto update(Integer id, ProductDto dto) {
        Product existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
        mapper.updateEntityFromDto(dto, existing);
        return mapper.toDto(repository.save(existing));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PRODUCTS_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PRODUCTS, allEntries = true),
            @CacheEvict(value = PRODUCTS_GET_BY_ID, key = "#id")
    })
    public void delete(Integer id) {
        Product existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
        repository.delete(existing);
    }
}

