package com.example.backend.service.person.address;

import com.example.backend.domain.model.person.address.Address;
import com.example.backend.domain.model.person.countryregion.CountryRegion;
import com.example.backend.domain.model.person.stateprovince.StateProvince;
import com.example.backend.domain.model.sales.salesterritory.SalesTerritory;
import com.example.backend.domain.repository.person.address.AddressRepository;
import com.example.backend.domain.repository.person.stateprovince.StateProvinceRepository;
import com.example.backend.dto.person.address.AddressDto;
import com.example.backend.mapper.person.address.AddressMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddressServiceImplTest {

    @InjectMocks
    private AddressServiceImpl addressService;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private StateProvinceRepository stateProvinceRepository;

    @Mock
    private AddressMapper addressMapper;

    private AddressDto dto;
    private Address entity;
    private StateProvince province;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        province = StateProvince.builder()
                .stateProvinceId(1)
                .name("Test_State")
                .countryRegion(mock(CountryRegion.class))      // required field
                .salesTerritory(mock(SalesTerritory.class))    // required field
                .isOnlyStateProvinceFlag(true)
                .build();

        entity = Address.builder()
                .addressId(1)
                .addressLine1("123 Main St")
                .city("Springfield")
                .postalCode("12345")
                .stateProvince(province)
                .build();

        dto = AddressDto.builder()
                .addressId(1)
                .addressLine1("123 Main St")
                .city("Springfield")
                .stateProvinceId(1)
                .postalCode("12345")
                .rowguid(UUID.randomUUID())
                .build();
    }

    @Test
    void create_ShouldReturnSavedDto() {
        when(stateProvinceRepository.findById(1)).thenReturn(Optional.of(province));
        when(addressMapper.toEntity(dto)).thenReturn(entity);
        when(addressRepository.save(entity)).thenReturn(entity);
        when(addressMapper.toDto(entity)).thenReturn(dto);

        AddressDto result = addressService.create(dto);

        assertEquals(dto.getAddressId(), result.getAddressId());
        verify(addressRepository).save(entity);
    }

    @Test
    void getById_ShouldReturnAddress() {
        when(addressRepository.findById(1)).thenReturn(Optional.of(entity));
        when(addressMapper.toDto(entity)).thenReturn(dto);

        AddressDto result = addressService.getById(1);

        assertEquals(dto.getAddressId(), result.getAddressId());
    }

    @Test
    void getById_ShouldThrowWhenNotFound() {
        when(addressRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> addressService.getById(99));
    }

    @Test
    void getAll_ShouldReturnList() {
        List<Address> list = List.of(entity);
        when(addressRepository.findAll()).thenReturn(list);
        when(addressMapper.toDto(entity)).thenReturn(dto);

        List<AddressDto> result = addressService.getAll();

        assertEquals(1, result.size());
    }

    @Test
    void update_ShouldUpdateAndReturnDto() {
        when(addressRepository.findById(1)).thenReturn(Optional.of(entity));
        doNothing().when(addressMapper).updateEntityFromDto(dto, entity);
        when(addressRepository.save(entity)).thenReturn(entity);
        when(addressMapper.toDto(entity)).thenReturn(dto);

        AddressDto result = addressService.update(1, dto);

        assertEquals(dto.getAddressId(), result.getAddressId());
    }

    @Test
    void update_ShouldThrowWhenNotFound() {
        when(addressRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> addressService.update(99, dto));
    }

    @Test
    void delete_ShouldRemoveAddress() {
        when(addressRepository.findById(1)).thenReturn(Optional.of(entity));
        doNothing().when(addressRepository).delete(entity);

        addressService.delete(1);

        verify(addressRepository).delete(entity);
    }

    @Test
    void delete_ShouldThrowWhenNotFound() {
        when(addressRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> addressService.delete(99));
    }

    @Test
    void getAllAddresses_ShouldReturnPagedResponse() {
        Page<Address> page = new PageImpl<>(List.of(entity));
        Pageable pageable = PageRequest.of(0, 10);

        when(addressRepository.findAll(pageable)).thenReturn(page);
        when(addressMapper.toDto(entity)).thenReturn(dto);

        var result = addressService.getPaginated(pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(0, result.getPage());
        assertTrue(result.isLast());
    }
}