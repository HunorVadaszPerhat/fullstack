package com.example.backend.service.production.productproductphoto;

import com.example.backend.domain.model.production.productproductphoto.ProductProductPhoto;
import com.example.backend.domain.model.production.productproductphoto.ProductProductPhotoId;
import com.example.backend.dto.production.productproductphoto.ProductProductPhotoDto;
import com.example.backend.mapper.production.productproductphoto.ProductProductPhotoMapper;
import com.example.backend.mapper.production.productproductphoto.ProductProductPhotoResolver;
import com.example.backend.repository.production.productproductphoto.ProductProductPhotoRepository;
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

@Service
@RequiredArgsConstructor
@Transactional
public class ProductProductPhotoServiceImpl implements ProductProductPhotoService {

    private final ProductProductPhotoRepository repository;
    private final ProductProductPhotoMapper mapper;
    private final ProductProductPhotoResolver resolver;

    @Override
    @Caching(evict = {
            @CacheEvict(value = "product-product-photos-get-all", allEntries = true),
            @CacheEvict(value = "search-product-product-photos", allEntries = true)
    })
    public ProductProductPhotoDto create(ProductProductPhotoDto dto) {
        ProductProductPhoto entity = mapper.toEntity(dto, resolver);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Cacheable(value = "product-product-photos-get-by-id", key = "#id.productId + '-' + #id.productPhotoId")
    public ProductProductPhotoDto getById(ProductProductPhotoId id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Not found"));
    }

    @Override
    @Cacheable(value = "product-product-photos-get-all")
    public List<ProductProductPhotoDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Cacheable(value = "search-product-product-photos", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<ProductProductPhotoDto> getPaginated(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "product-product-photos-get-by-id", key = "#id.productId + '-' + #id.productPhotoId"),
            @CacheEvict(value = "product-product-photos-get-all", allEntries = true),
            @CacheEvict(value = "search-product-product-photos", allEntries = true)
    })
    public ProductProductPhotoDto update(ProductProductPhotoId id, ProductProductPhotoDto dto) {
        ProductProductPhoto existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found"));
        existing.setIsPrimary(dto.getIsPrimary());
        existing.setModifiedDate(dto.getModifiedDate());
        return mapper.toDto(repository.save(existing));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "product-product-photos-get-by-id", key = "#id.productId + '-' + #id.productPhotoId"),
            @CacheEvict(value = "product-product-photos-get-all", allEntries = true),
            @CacheEvict(value = "search-product-product-photos", allEntries = true)
    })
    public void delete(ProductProductPhotoId id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Not found");
        }
        repository.deleteById(id);
    }
}

