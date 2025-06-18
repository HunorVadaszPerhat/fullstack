package com.example.backend.service.production.productmodelproductdescriptionculture;

import com.example.backend.domain.model.person.address.Address;
import com.example.backend.domain.model.production.culture.Culture;
import com.example.backend.domain.model.production.productdescription.ProductDescription;
import com.example.backend.domain.model.production.productmodel.ProductModel;
import com.example.backend.domain.model.production.productmodelproductdescriptionculture.ProductModelProductDescriptionCulture;
import com.example.backend.domain.model.production.productmodelproductdescriptionculture.ProductModelProductDescriptionCultureId;
import com.example.backend.dto.person.address.AddressDto;
import com.example.backend.dto.production.productmodelproductdescriptionculture.ProductModelProductDescriptionCultureDto;
import com.example.backend.mapper.production.productmodelproductdescriptionculture.ProductModelProductDescriptionCultureMapper;
import com.example.backend.repository.production.culture.CultureRepository;
import com.example.backend.repository.production.productdescription.ProductDescriptionRepository;
import com.example.backend.repository.production.productmodel.ProductModelRepository;
import com.example.backend.repository.production.productmodelproductdescriptionculture.ProductModelProductDescriptionCultureRepository;
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
public class ProductModelProductDescriptionCultureServiceImpl implements ProductModelProductDescriptionCultureService {

    private final ProductModelProductDescriptionCultureRepository repository;
    private final ProductModelRepository productModelRepository;
    private final ProductDescriptionRepository productDescriptionRepository;
    private final CultureRepository cultureRepository;
    private final ProductModelProductDescriptionCultureMapper mapper;

    @Override
    @Caching(evict = {
            @CacheEvict(value = PRODUCT_MODEL_DESCRIPTION_CULTURES_GET_BY_ID, key = "#dto.productModelId + '-' + #dto.productDescriptionId + '-' + #dto.cultureId", condition = "#result != null"),
            @CacheEvict(value = PRODUCT_MODEL_DESCRIPTION_CULTURES_GET_ALL, allEntries = true),
            @CacheEvict(value = SEARCH_PRODUCT_MODEL_DESCRIPTION_CULTURES, allEntries = true)
    })
    @Timed(value = "productModelProductDescriptionCulture.create", description = "Time taken to create ProductModelProductDescriptionCulture")
    public ProductModelProductDescriptionCultureDto create(ProductModelProductDescriptionCultureDto dto) {
        ProductModel model = productModelRepository.findById(dto.getProductModelId())
                .orElseThrow(() -> new EntityNotFoundException("ProductModel not found"));

        ProductDescription desc = productDescriptionRepository.findById(dto.getProductDescriptionId())
                .orElseThrow(() -> new EntityNotFoundException("ProductDescription not found"));

        Culture culture = cultureRepository.findById(dto.getCultureId())
                .orElseThrow(() -> new EntityNotFoundException("Culture not found"));

        var entity = mapper.toEntity(dto, model, desc, culture);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Cacheable(value = PRODUCT_MODEL_DESCRIPTION_CULTURES_GET_BY_ID,key = "#id")
    @Timed(value = "productModelProductDescriptionCulture.get-by-id", description = "Time to fetch product ProductModelProductDescriptionCulture by ID")
    public ProductModelProductDescriptionCultureDto getById(ProductModelProductDescriptionCultureId id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found")));
    }

    @Override
    @Cacheable(PRODUCT_MODEL_DESCRIPTION_CULTURES_GET_ALL)
    @Timed(value = "productModelProductDescriptionCulture.get-all", description = "Time to fetch product all ProductModelProductDescriptionCulture")
    public List<ProductModelProductDescriptionCultureDto> getAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public PagedResponse<ProductModelProductDescriptionCultureDto> getPaginated(Pageable pageable) {
        Page<ProductModelProductDescriptionCulture> productModelProductDescriptionCulturePage = repository.findAll(pageable);
        List<ProductModelProductDescriptionCultureDto> content = productModelProductDescriptionCulturePage.getContent()
                .stream()
                .map(mapper::toDto)
                .toList();
        return new PagedResponse<>(
                content,
                productModelProductDescriptionCulturePage.getNumber(),
                productModelProductDescriptionCulturePage.getSize(),
                productModelProductDescriptionCulturePage.getTotalElements(),
                productModelProductDescriptionCulturePage.getTotalPages(),
                productModelProductDescriptionCulturePage.isLast()
        );
    }

    @Override
    @CacheEvict(value = "product-model-description-cultures-get-by-id", key = "#id")
    public void delete(ProductModelProductDescriptionCultureId id) {
        repository.deleteById(id);
    }
}

