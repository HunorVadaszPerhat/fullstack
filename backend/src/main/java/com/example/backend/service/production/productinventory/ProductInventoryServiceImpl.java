package com.example.backend.service.production.productinventory;

import com.example.backend.domain.model.production.location.Location;
import com.example.backend.domain.model.production.product.Product;
import com.example.backend.domain.model.production.productiventory.ProductInventory;
import com.example.backend.domain.model.production.productiventory.ProductInventoryId;
import com.example.backend.dto.production.productinventory.ProductInventoryDto;
import com.example.backend.mapper.production.productinventory.ProductInventoryMapper;
import com.example.backend.repository.production.location.LocationRepository;
import com.example.backend.repository.production.product.ProductRepository;
import com.example.backend.repository.production.productinventory.ProductInventoryRepository;
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
public class ProductInventoryServiceImpl implements ProductInventoryService {

    private final ProductInventoryRepository repository;
    private final ProductRepository productRepository;
    private final LocationRepository locationRepository;
    private final ProductInventoryMapper mapper;

    @Override
    @Caching(evict = {
            @CacheEvict(value = PRODUCT_INVENTORIES_GET_ALL, key = "#dto.productId + '-' + #dto.locationId", condition = "#result != null"),
            @CacheEvict(value = PRODUCT_INVENTORIES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PRODUCT_INVENTORIES, allEntries = true)
    })
    @Timed(value = "address.create", description = "Time taken to create address")
    public ProductInventoryDto create(ProductInventoryDto dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        Location location = locationRepository.findById(dto.getLocationId().shortValue())
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));

        ProductInventory entity = mapper.toEntity(dto, product, location);
        ProductInventory saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Cacheable(value = PRODUCT_INVENTORIES_GET_BY_ID, key = "#productId + '-' + #locationId")
    @Timed(value = "productInventory.get-by-id", description = "Time to fetch product productInventory by ID")
    public ProductInventoryDto getById(Integer productId, Integer locationId) {
        ProductInventoryId id = new ProductInventoryId(productId, locationId);
        ProductInventory entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inventory not found"));
        return mapper.toDto(entity);
    }

    @Override
    @Cacheable(value = PRODUCT_INVENTORIES_GET_ALL)
    @Timed(value = "productInventory.get-all", description = "Time taken to get all product productInventories")
    public List<ProductInventoryDto> getAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    @Cacheable(value = SEARCH_PRODUCT_INVENTORIES, key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    @Timed(value = "productInventory.get-paginated", description = "Time taken to get paginated product productInventories")
    public PagedResponse<ProductInventoryDto> getPaginated(Pageable pageable) {
        Page<ProductInventory> page = repository.findAll(pageable);
        return new PagedResponse<>(
                page.getContent().stream().map(mapper::toDto).toList(),
                page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isLast()
        );
    }


    @Override
    @Caching(evict = {
            @CacheEvict(value = PRODUCT_INVENTORIES_GET_BY_ID, key = "#productId + '-' + #locationId"),
            @CacheEvict(value = PRODUCT_INVENTORIES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PRODUCT_INVENTORIES, allEntries = true)
    })
    @Timed(value = "productInventory.update", description = "Time taken to update productInventory")
    public ProductInventoryDto update(Integer productId, Integer locationId, ProductInventoryDto dto) {
        ProductInventoryId id = new ProductInventoryId(productId, locationId);
        ProductInventory entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inventory not found"));

        mapper.updateEntity(entity, dto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PRODUCT_INVENTORIES_GET_BY_ID, key = "#productId + '-' + #locationId"),
            @CacheEvict(value = PRODUCT_INVENTORIES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PRODUCT_INVENTORIES, allEntries = true)
    })
    @Timed(value = "productInventory.delete", description = "Time taken to delete productInventory")
    public void delete(Integer productId, Integer locationId) {
        ProductInventoryId id = new ProductInventoryId(productId, locationId);
        repository.deleteById(id);
    }
}
