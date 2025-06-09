package com.example.backend.mapper.person.address;

import com.example.backend.domain.model.person.address.Address;
import com.example.backend.domain.model.person.stateprovince.StateProvince;
import com.example.backend.domain.repository.person.stateprovince.StateProvinceRepository;
import com.example.backend.dto.person.address.AddressDto;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class AddressMapper {

    @Autowired
    protected StateProvinceRepository stateProvinceRepository;

    @Mapping(source = "stateProvince.stateProvinceId", target = "stateProvinceId")
    public abstract AddressDto toDto(Address address);

    @Mapping(source = "stateProvinceId", target = "stateProvince.stateProvinceId")
    public abstract Address toEntity(AddressDto addressDto);

    @Mapping(source = "stateProvinceId", target = "stateProvince.stateProvinceId")
    public abstract void updateEntityFromDto(AddressDto dto, @MappingTarget Address entity);

    /**
     * ObjectFactory that tries to fetch StateProvince from DB using the ID in the DTO.
     * This prevents nulls and ensures only valid relationships are set.
     */
    @ObjectFactory
    protected StateProvince resolveStateProvince(AddressDto dto) {
        if (dto.getStateProvinceId() == null) {
            throw new IllegalArgumentException("StateProvinceId must not be null");
        }

        return stateProvinceRepository.findById(dto.getStateProvinceId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "StateProvince not found with id: " + dto.getStateProvinceId()
                ));
    }
}
