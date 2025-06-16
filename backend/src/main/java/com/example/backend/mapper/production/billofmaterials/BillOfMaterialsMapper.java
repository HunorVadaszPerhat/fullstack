package com.example.backend.mapper.production.billofmaterials;

import com.example.backend.domain.model.production.billofmaterials.BillOfMaterials;
import com.example.backend.dto.production.billofmaterials.BillOfMaterialsDto;
import org.springframework.stereotype.Component;

@Component
public class BillOfMaterialsMapper {

    public BillOfMaterialsDto toDto(BillOfMaterials entity) {
        return BillOfMaterialsDto.builder()
                .billOfMaterialsId(entity.getBillOfMaterialsId())
                .productAssemblyId(entity.getProductAssembly() != null ? entity.getProductAssembly().getProductId() : null)
                .componentId(entity.getComponent().getProductId())
                .unitMeasureCode(entity.getUnitMeasure().getUnitMeasureCode())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .bomLevel(entity.getBomLevel())
                .perAssemblyQty(entity.getPerAssemblyQty())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public BillOfMaterials toEntity(BillOfMaterialsDto dto, BillOfMaterialsResolver resolver) {
        return BillOfMaterials.builder()
                .billOfMaterialsId(dto.getBillOfMaterialsId())
                .productAssembly(dto.getProductAssemblyId() != null ? resolver.resolveProduct(dto.getProductAssemblyId()) : null)
                .component(resolver.resolveProduct(dto.getComponentId()))
                .unitMeasure(resolver.resolveUnitMeasure(dto.getUnitMeasureCode()))
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .bomLevel(dto.getBomLevel())
                .perAssemblyQty(dto.getPerAssemblyQty())
                .modifiedDate(dto.getModifiedDate())
                .build();
    }

    public void updateEntityFromDto(BillOfMaterialsDto dto, BillOfMaterials entity, BillOfMaterialsResolver resolver) {
        if (dto.getProductAssemblyId() != null)
            entity.setProductAssembly(resolver.resolveProduct(dto.getProductAssemblyId()));
        if (dto.getComponentId() != null)
            entity.setComponent(resolver.resolveProduct(dto.getComponentId()));
        if (dto.getUnitMeasureCode() != null)
            entity.setUnitMeasure(resolver.resolveUnitMeasure(dto.getUnitMeasureCode()));
        if (dto.getStartDate() != null) entity.setStartDate(dto.getStartDate());
        if (dto.getEndDate() != null) entity.setEndDate(dto.getEndDate());
        if (dto.getBomLevel() != null) entity.setBomLevel(dto.getBomLevel());
        if (dto.getPerAssemblyQty() != null) entity.setPerAssemblyQty(dto.getPerAssemblyQty());
    }
}

