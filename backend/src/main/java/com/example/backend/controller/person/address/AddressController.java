package com.example.backend.controller.person.address;

import com.example.backend.dto.person.address.AddressDto;
import com.example.backend.service.person.address.AddressService;
import com.example.backend.util.response.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @GetMapping("/paginated")
    @Operation(summary = "Get paginated addresses", description = "Returns a page of addresses with sorting options.")
    @ApiResponse(responseCode = "200", description = "Page retrieved successfully")
    public PagedResponse<AddressDto> getAllAddresses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "addressId,asc") String[] sort
    ) {
        String sortBy = (sort.length > 0 && sort[0] != null && !sort[0].isBlank())
                ? sort[0]
                : "addressId";

        Sort.Direction direction = (sort.length > 1 && "desc".equalsIgnoreCase(sort[1]))
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return addressService.getAllAddresses(pageable);
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

    @Operation(summary = "Update address", description = "Updates an address by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address successfully updated"),
            @ApiResponse(responseCode = "404", description = "Address not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AddressDto> update(
            @Parameter(description = "ID of the address to update") @PathVariable Integer id,
            @RequestBody @Valid AddressDto dto) {
        return ResponseEntity.ok(addressService.update(id, dto));
    }

    @Operation(summary = "Delete address", description = "Deletes an address by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Address successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Address not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the address to delete") @PathVariable Integer id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
