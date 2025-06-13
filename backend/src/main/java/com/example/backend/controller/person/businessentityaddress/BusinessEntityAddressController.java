package com.example.backend.controller.person.businessentityaddress;

import com.example.backend.dto.person.businessentityaddress.BusinessEntityAddressDto;
import com.example.backend.service.person.businessentityaddress.BusinessEntityAddressService;
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
@RequestMapping("/api/business-entity-addresses")
@RequiredArgsConstructor
public class BusinessEntityAddressController {

    private final BusinessEntityAddressService service;

    @Operation(summary = "Create business entity address", description = "Creates a new association between a business entity and an address.")
    @ApiResponse(responseCode = "200", description = "Business entity address successfully created")
    @PostMapping
    public ResponseEntity<BusinessEntityAddressDto> create(
            @RequestBody @Valid BusinessEntityAddressDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Get business entity address by composite key", description = "Fetches the association using businessEntityID, addressID, and addressTypeID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Business entity address found"),
            @ApiResponse(responseCode = "404", description = "Business entity address not found")
    })
    @GetMapping("/{businessEntityID}/{addressID}/{addressTypeID}")
    public ResponseEntity<BusinessEntityAddressDto> getById(
            @Parameter(description = "Business entity ID") @PathVariable Integer businessEntityID,
            @Parameter(description = "Address ID") @PathVariable Integer addressID,
            @Parameter(description = "Address type ID") @PathVariable Integer addressTypeID) {
        return ResponseEntity.ok(service.getById(businessEntityID, addressID, addressTypeID));
    }

    @Operation(summary = "Get all business entity addresses", description = "Returns all business entity address associations.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @GetMapping
    public ResponseEntity<List<BusinessEntityAddressDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get paginated business entity addresses", description = "Returns a page of business entity address records with sorting options.")
    @ApiResponse(responseCode = "200", description = "Page retrieved successfully")
    @GetMapping("/paginated")
    public PagedResponse<BusinessEntityAddressDto> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "businessEntityID,asc") String[] sort
    ) {
        String sortBy = (sort.length > 0 && sort[0] != null && !sort[0].isBlank())
                ? sort[0]
                : "businessEntityID";

        Sort.Direction direction = (sort.length > 1 && "desc".equalsIgnoreCase(sort[1]))
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return service.getPaginated(pageable);
    }

    @Operation(summary = "Update business entity address", description = "Updates an existing association using the composite key.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Business entity address successfully updated"),
            @ApiResponse(responseCode = "404", description = "Business entity address not found")
    })
    @PutMapping
    public ResponseEntity<BusinessEntityAddressDto> update(
            @RequestBody @Valid BusinessEntityAddressDto dto) {
        return ResponseEntity.ok(service.update(dto));
    }

    @Operation(summary = "Delete business entity address", description = "Deletes the association using the composite key.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Business entity address successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Business entity address not found")
    })
    @DeleteMapping("/{businessEntityID}/{addressID}/{addressTypeID}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Business entity ID") @PathVariable Integer businessEntityID,
            @Parameter(description = "Address ID") @PathVariable Integer addressID,
            @Parameter(description = "Address type ID") @PathVariable Integer addressTypeID) {
        service.delete(businessEntityID, addressID, addressTypeID);
        return ResponseEntity.noContent().build();
    }
}
