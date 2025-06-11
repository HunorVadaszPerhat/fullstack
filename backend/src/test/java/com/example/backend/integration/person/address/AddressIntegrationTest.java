package com.example.backend.integration.person.address;

import com.example.backend.dto.person.address.AddressDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "/sql/insert-stateprovince.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class AddressIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateAndFetchAddress() throws Exception {
        AddressDto dto = AddressDto.builder()
                .addressLine1("123 Integration St")
                .city("MySQLville")
                .stateProvinceId(1)
                .postalCode("12345")
                .rowguid(UUID.randomUUID())
                .modifiedDate(new Date())
                .build();

        String createResponse = mockMvc.perform(post("/api/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("MySQLville"))
                .andReturn().getResponse().getContentAsString();

        AddressDto created = objectMapper.readValue(createResponse, AddressDto.class);

        mockMvc.perform(get("/api/address/" + created.getAddressId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.addressLine1").value("123 Integration St"));
    }

    @Test
    void shouldReturnEmptyListInitially() throws Exception {
        mockMvc.perform(get("/api/address"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
