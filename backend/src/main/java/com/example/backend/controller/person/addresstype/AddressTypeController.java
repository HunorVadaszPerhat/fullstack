package com.example.backend.controller.person.addresstype;

import com.example.backend.dto.person.addresstype.AddressTypeDto;
import com.example.backend.service.person.addresstype.AddressTypeService;
import com.example.backend.util.response.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address-type")
@RequiredArgsConstructor
@Tag(name = "Address Type", description = "Address Type management APIs")
public class AddressTypeController {

    private final AddressTypeService addressTypeService;

    @Operation(summary = "Get all address types", description = "Returns a list of all address types.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @GetMapping
    public ResponseEntity<List<AddressTypeDto>> getAll() {
        return ResponseEntity.ok(addressTypeService.getAll());
    }

    @Operation(summary = "Get address type by ID", description = "Returns an address type for the given ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address type found"),
            @ApiResponse(responseCode = "404", description = "Address type not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AddressTypeDto> getById(
            @Parameter(description = "ID of the address type") @PathVariable Integer id) {
        return ResponseEntity.ok(addressTypeService.getById(id));
    }

    @GetMapping("/paginated")
    @Operation(summary = "Get paginated address types", description = "Returns a page of address types with sorting options.")
    @ApiResponse(responseCode = "200", description = "Page retrieved successfully")
    public PagedResponse<AddressTypeDto> getAllAddressTypes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "addressTypeId,asc") String[] sort
    ) {
        String sortBy = (sort.length > 0 && sort[0] != null && !sort[0].isBlank())
                ? sort[0]
                : "addressTypeId";

        Sort.Direction direction = (sort.length > 1 && "desc".equalsIgnoreCase(sort[1]))
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return addressTypeService.getAllAddressTypes(pageable);
    }

    @Operation(summary = "Create a new address type", description = "Adds a new address type.")
    @ApiResponse(responseCode = "200", description = "Address type successfully created")
    @PostMapping
    public ResponseEntity<AddressTypeDto> create(
            @RequestBody @Valid AddressTypeDto dto) {
        return ResponseEntity.ok(addressTypeService.create(dto));
    }

    @Operation(summary = "Update address type", description = "Updates an address type by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address type successfully updated"),
            @ApiResponse(responseCode = "404", description = "Address type not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AddressTypeDto> update(
            @Parameter(description = "ID of the address type to update") @PathVariable Integer id,
            @RequestBody @Valid AddressTypeDto dto) {
        return ResponseEntity.ok(addressTypeService.update(id, dto));
    }

    @Operation(summary = "Delete address type", description = "Deletes an address type by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Address type successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Address type not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the address type to delete") @PathVariable Integer id) {
        addressTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}


