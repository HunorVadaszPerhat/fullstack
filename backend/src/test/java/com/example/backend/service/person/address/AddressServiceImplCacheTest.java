package com.example.backend.service.person.address;

import com.example.backend.domain.model.person.address.Address;
import com.example.backend.domain.repository.person.address.AddressRepository;
import com.example.backend.dto.person.address.AddressDto;
import com.example.backend.mapper.person.address.AddressMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Import(AddressServiceImplCacheTest.Config.class)
class AddressServiceImplCacheTest {

    @TestConfiguration
    static class Config {

        @Bean
        public AddressService addressService(AddressRepository repo, AddressMapper mapper) {
            return new AddressServiceImpl(repo, mapper, null);
        }

        @Bean
        public CacheManager cacheManager() {
            return new ConcurrentMapCacheManager("addresses");
        }
    }

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    private AddressService addressService;

    private Pageable pageable;

    @BeforeEach
    void setup() {
        CacheManager cacheManager = new ConcurrentMapCacheManager("addresses");

        addressService = new AddressServiceImpl(addressRepository, addressMapper, null);

        pageable = PageRequest.of(0, 5, Sort.by("city").ascending());

        Address address = new Address();
        address.setAddressId(1);
        address.setCity("Berlin");

        AddressDto addressDto = new AddressDto();
        addressDto.setAddressId(1);
        addressDto.setCity("Berlin");

        when(addressRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(address)));
        when(addressMapper.toDto(address)).thenReturn(addressDto);

        Objects.requireNonNull(cacheManager.getCache("addresses")).clear();
    }

    @Test
    void getAllAddresses_shouldCacheResult() {
        Page<AddressDto> firstCall = addressService.getAllAddresses(pageable);
        Page<AddressDto> secondCall = addressService.getAllAddresses(pageable);

        assertEquals(1, firstCall.getContent().size());
        assertEquals(1, secondCall.getContent().size());

        verify(addressRepository, times(1)).findAll(pageable);
    }
}