package com.example.backend.service.person.address;

import com.example.backend.domain.repository.person.address.AddressRepository;
import com.example.backend.dto.person.address.AddressDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AddressServiceImplCacheRedisTest {
    @Autowired
    private AddressService addressService;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void clearCache() {
        cacheManager.getCache("addresses").clear();
    }

    @Test
    void testRedisBackedCaching() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("city"));

        Page<AddressDto> first = addressService.getAllAddresses(pageable);
        Page<AddressDto> second = addressService.getAllAddresses(pageable);

        // Should return the same result (second one cached)
        assertEquals(first.getContent(), second.getContent());
    }
}

