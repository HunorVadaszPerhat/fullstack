package com.example.backend.service.person.address;

import com.example.backend.domain.model.person.address.Address;
import com.example.backend.domain.repository.person.address.AddressRepository;
import com.example.backend.dto.person.address.AddressDto;
import com.example.backend.mapper.person.address.AddressMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private AddressServiceImpl addressService;

    @SuppressWarnings("unchecked")
    @Test
    void testSearchAddresses_withAllFilters_returnsPagedResults() {
        // GIVEN: filter parameters
        String city = "Berlin";
        String postalCode = "10115"; // ✅ renamed from zipCode
        Integer stateProvinceId = 7;
        Pageable pageable = PageRequest.of(0, 10);

        // GIVEN: mock Address entity
        Address address = new Address();
        address.setAddressId(1); // ✅ Integer not Long
        address.setCity(city);
        address.setPostalCode(postalCode);

        // Mock AddressDto
        AddressDto dto = new AddressDto();
        dto.setAddressId(1); // Assuming DTO uses `id`
        dto.setCity(city);
        dto.setPostalCode(postalCode);

        Page<Address> addressPage = new PageImpl<>(List.of(address));

        // WHEN
        when(addressRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(addressPage);
        when(addressMapper.toDto(address)).thenReturn(dto);

        // EXECUTE
        Page<AddressDto> result = addressService.searchAddresses(city, postalCode, stateProvinceId, pageable);

        // VERIFY
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Berlin", result.getContent().getFirst().getCity());

        verify(addressRepository).findAll(any(Specification.class), eq(pageable));
        verify(addressMapper).toDto(address);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testSearchAddresses_cityOnly_returnsResults() {
        // GIVEN
        String city = "Toronto";
        Pageable pageable = PageRequest.of(0, 5);

        Address address = new Address();
        address.setAddressId(2);
        address.setCity(city);

        AddressDto dto = new AddressDto();
        dto.setAddressId(2);
        dto.setCity(city);

        Page<Address> page = new PageImpl<>(List.of(address));

        when(addressRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);
        when(addressMapper.toDto(address)).thenReturn(dto);

        // WHEN
        Page<AddressDto> result = addressService.searchAddresses(city, null, null, pageable);

        // THEN
        assertEquals(1, result.getTotalElements());
        assertEquals("Toronto", result.getContent().getFirst().getCity());

        verify(addressRepository).findAll(any(Specification.class), eq(pageable));
        verify(addressMapper).toDto(address);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testSearchAddresses_allFiltersNull_returnsAll() {
        // GIVEN
        Pageable pageable = PageRequest.of(0, 10);
        Address address = new Address();
        address.setAddressId(3);
        address.setCity("Rome");

        AddressDto dto = new AddressDto();
        dto.setAddressId(3);
        dto.setCity("Rome");

        Page<Address> page = new PageImpl<>(List.of(address));

        when(addressRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);
        when(addressMapper.toDto(address)).thenReturn(dto);

        // WHEN
        Page<AddressDto> result = addressService.searchAddresses(null, null, null, pageable);

        // THEN
        assertEquals(1, result.getTotalElements());
        assertEquals("Rome", result.getContent().getFirst().getCity());

        verify(addressRepository).findAll(any(Specification.class), eq(pageable));
    }

}


