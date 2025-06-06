package com.example.backend.controller.person.address;

import com.example.backend.dto.person.address.AddressDto;
import com.example.backend.dto.person.person.PersonDto;
import com.example.backend.service.person.address.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @Operation(summary = "Get all addresses", description = "Returns a list of all addresses.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @GetMapping
    public ResponseEntity<List<AddressDto>> getAll() {
        return ResponseEntity.ok(addressService.getAll());
    }

    @Operation(summary = "Get address by ID", description = "Returns an address for the given ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address found"),
            @ApiResponse(responseCode = "404", description = "Address not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> getById(
            @Parameter(description = "ID of the address") @PathVariable Integer id) {
        return ResponseEntity.ok(addressService.getById(id));
    }

    @Operation(summary = "Create a new address", description = "Adds a new address with linked stateprovince entity.")
    @ApiResponse(responseCode = "200", description = "Address successfully created")
    @PostMapping
    public ResponseEntity<AddressDto> create(
            @RequestBody @Valid AddressDto dto) {
        return ResponseEntity.ok(addressService.create(dto));
    }
}
