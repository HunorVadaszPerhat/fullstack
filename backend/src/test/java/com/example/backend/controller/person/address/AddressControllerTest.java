package com.example.backend.controller.person.address;

import com.example.backend.dto.person.address.AddressDto;
import com.example.backend.service.person.address.AddressService;
import com.example.backend.util.response.PagedResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AddressController.class)
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal") // suppresses deprecation warning
    @MockBean
    private AddressService addressService;

    @Autowired
    private ObjectMapper objectMapper;

    private AddressDto sampleDto;

    @BeforeEach
    void setUp() {
        sampleDto = AddressDto.builder()
                .addressId(1)
                .addressLine1("123 Main St")
                .city("Springfield")
                .stateProvinceId(1)
                .postalCode("12345")
                .rowguid(UUID.randomUUID())
                .build();
    }

    @Test
    void shouldReturnAllAddresses() throws Exception {
        when(addressService.getAll()).thenReturn(List.of(sampleDto));

        mockMvc.perform(get("/api/address"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].city").value("Springfield"));
    }

    @Test
    void shouldReturnPaginatedAddresses() throws Exception {
        PagedResponse<AddressDto> paged = new PagedResponse<>(
                List.of(sampleDto), 0, 10, 1, 1, true
        );

        when(addressService.getPaginated(any())).thenReturn(paged);

        mockMvc.perform(get("/api/address/paginated?page=0&size=10&sort=addressId,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].city").value("Springfield"));
    }

    @Test
    void shouldReturnAddressById() throws Exception {
        when(addressService.getById(1)).thenReturn(sampleDto);

        mockMvc.perform(get("/api/address/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.addressId").value(1));
    }

    @Test
    void shouldCreateNewAddress() throws Exception {
        when(addressService.create(any(AddressDto.class))).thenReturn(sampleDto);

        mockMvc.perform(post("/api/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.addressId").value(1));
    }

    @Test
    void shouldUpdateAddress() throws Exception {
        when(addressService.update(eq(1), any(AddressDto.class))).thenReturn(sampleDto);

        mockMvc.perform(put("/api/address/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.addressId").value(1));
    }

    @Test
    void shouldDeleteAddress() throws Exception {
        doNothing().when(addressService).delete(1);

        mockMvc.perform(delete("/api/address/1"))
                .andExpect(status().isNoContent());
    }
}
