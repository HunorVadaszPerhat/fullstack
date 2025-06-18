package com.example.backend.service.production.productphoto;

import com.example.backend.domain.model.production.productphoto.ProductPhoto;
import com.example.backend.dto.production.productphoto.ProductPhotoDto;
import com.example.backend.mapper.production.productphoto.ProductPhotoMapper;
import com.example.backend.repository.production.productphoto.ProductPhotoRepository;
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
public class ProductPhotoServiceImpl implements ProductPhotoService {

    private final ProductPhotoRepository repository;
    private final ProductPhotoMapper mapper;

    @Override
    @Caching(evict = {
            @CacheEvict(value = PRODUCT_PHOTOS_GET_ALL, allEntries = true),
            @CacheEvict(value = PRODUCT_PHOTOS_GET_BY_ID, key = "#dto.productPhotoId"),
            @CacheEvict(value = SEARCH_PRODUCT_PHOTOS, allEntries = true)
    })
    public ProductPhotoDto create(ProductPhotoDto dto) {
        ProductPhoto entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Cacheable(value = PRODUCT_PHOTOS_GET_BY_ID, key = "#id")
    @Timed(value = "productPhoto.getById", description = "Time taken to fetch product photo by ID")
    public ProductPhotoDto getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("ProductPhoto not found"));
    }

    @Override
    @Cacheable(value = PRODUCT_PHOTOS_GET_ALL)
    public List<ProductPhotoDto> getAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public PagedResponse<ProductPhotoDto> getPaginated(Pageable pageable) {
        Page<ProductPhoto> page = repository.findAll(pageable);
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
    @Caching(evict = {
            @CacheEvict(value = PRODUCT_PHOTOS_GET_ALL, allEntries = true),
            @CacheEvict(value = PRODUCT_PHOTOS_GET_BY_ID, key = "#id"),
            @CacheEvict(value = SEARCH_PRODUCT_PHOTOS, allEntries = true)
    })
    public ProductPhotoDto update(Integer id, ProductPhotoDto dto) {
        ProductPhoto entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProductPhoto not found"));
        mapper.updateEntityFromDto(dto, entity);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = PRODUCT_PHOTOS_GET_ALL, allEntries = true),
            @CacheEvict(value = PRODUCT_PHOTOS_GET_BY_ID, key = "#id"),
            @CacheEvict(value = SEARCH_PRODUCT_PHOTOS, allEntries = true)
    })
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("ProductPhoto not found");
        }
        repository.deleteById(id);
    }
}

