package com.example.backend.service.production.billofmaterials;

import com.example.backend.dto.production.billofmaterials.BillOfMaterialsDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BillOfMaterialsService {
    BillOfMaterialsDto create(BillOfMaterialsDto dto);
    BillOfMaterialsDto getById(Integer id);
    List<BillOfMaterialsDto> getAll();
    PagedResponse<BillOfMaterialsDto> getPaginated(Pageable pageable);
    BillOfMaterialsDto update(Integer id, BillOfMaterialsDto dto);
    void delete(Integer id);
}

