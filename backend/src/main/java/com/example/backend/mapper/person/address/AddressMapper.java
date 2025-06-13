package com.example.backend.mapper.person.address;

import com.example.backend.domain.model.person.address.Address;
import com.example.backend.domain.model.person.stateprovince.StateProvince;
import com.example.backend.repository.person.stateprovince.StateProvinceRepository;
import com.example.backend.dto.person.address.AddressDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressMapper {

    private final StateProvinceRepository stateProvinceRepository;

    public AddressDto toDto(Address entity) {
        if (entity == null) return null;

        return AddressDto.builder()
                .addressId(entity.getAddressId())
                .addressLine1(entity.getAddressLine1())
                .addressLine2(entity.getAddressLine2())
                .city(entity.getCity())
                .stateProvinceId(
                        entity.getStateProvince() != null ? entity.getStateProvince().getStateProvinceId() : null
                )
                .postalCode(entity.getPostalCode())
                .spatialLocation(entity.getSpatialLocation())
                .rowguid(entity.getRowguid())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public Address toEntity(AddressDto dto) {
        if (dto == null) return null;

        return Address.builder()
                .addressId(dto.getAddressId())
                .addressLine1(dto.getAddressLine1())
                .addressLine2(dto.getAddressLine2())
                .city(dto.getCity())
                .postalCode(dto.getPostalCode())
                .spatialLocation(dto.getSpatialLocation())
                .rowguid(dto.getRowguid())
                .modifiedDate(dto.getModifiedDate())
                .stateProvince(resolveStateProvince(dto.getStateProvinceId()))
                .build();
    }

    public void updateEntityFromDto(AddressDto dto, Address entity) {
        if (dto == null || entity == null) return;

        if (dto.getAddressLine1() != null) entity.setAddressLine1(dto.getAddressLine1());
        if (dto.getAddressLine2() != null) entity.setAddressLine2(dto.getAddressLine2());
        if (dto.getCity() != null) entity.setCity(dto.getCity());
        if (dto.getPostalCode() != null) entity.setPostalCode(dto.getPostalCode());
        if (dto.getSpatialLocation() != null) entity.setSpatialLocation(dto.getSpatialLocation());
        if (dto.getRowguid() != null) entity.setRowguid(dto.getRowguid());

        if (dto.getStateProvinceId() != null) {
            entity.setStateProvince(resolveStateProvince(dto.getStateProvinceId()));
        }
    }

    private StateProvince resolveStateProvince(Integer stateProvinceId) {
        if (stateProvinceId == null) {
            throw new IllegalArgumentException("StateProvinceId must not be null");
        }

        return stateProvinceRepository.findById(stateProvinceId)
                .orElseThrow(() -> new EntityNotFoundException("StateProvince not found: " + stateProvinceId));
    }
}
