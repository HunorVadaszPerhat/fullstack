package com.example.backend.service.production.productmodelproductdescriptionculture;

import com.example.backend.domain.model.production.productmodelproductdescriptionculture.ProductModelProductDescriptionCultureId;
import com.example.backend.dto.production.productmodelproductdescriptionculture.ProductModelProductDescriptionCultureDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductModelProductDescriptionCultureService {
    ProductModelProductDescriptionCultureDto create(ProductModelProductDescriptionCultureDto dto);
    ProductModelProductDescriptionCultureDto getById(ProductModelProductDescriptionCultureId id);
    List<ProductModelProductDescriptionCultureDto> getAll();
    PagedResponse<ProductModelProductDescriptionCultureDto> getPaginated(Pageable pageable);
    void delete(ProductModelProductDescriptionCultureId id);
}
