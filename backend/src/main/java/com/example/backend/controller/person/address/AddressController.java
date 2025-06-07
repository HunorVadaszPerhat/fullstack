package com.example.backend.controller.person.address;

import com.example.backend.dto.person.address.AddressDto;
import com.example.backend.service.person.address.AddressService;
import com.example.backend.util.sorting.SortUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @Operation(summary = "Get all addresses", description = "Retrieves a paginated list of all addresses.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Addresses successfully retrieved")
    })
    @GetMapping("/paged")
    public ResponseEntity<Page<AddressDto>> getAllAddresses(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of records per page") @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(addressService.getAllAddresses(pageable));
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

    @Operation(summary = "Search addresses by city", description = "Retrieves a paginated list of addresses filtered by city name.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Filtered addresses successfully retrieved")
    })
    @GetMapping("/search")
    public ResponseEntity<Page<AddressDto>> searchByCity(
            @Parameter(description = "City name to filter addresses by") @RequestParam String city,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of records per page") @RequestParam(defaultValue = "10") int size) {

        // Construct a Pageable object with requested page and size
        Pageable pageable = PageRequest.of(page, size);

        // Delegate to service layer and return the filtered result
        return ResponseEntity.ok(addressService.getAddressesByCity(city, pageable));
    }

    @Operation(summary = "Advanced search for addresses", description = "Filter addresses using city, zip code, and state/province ID. All filters are optional.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Filtered results returned successfully")
    })
    @GetMapping("/search-advanced")
    public ResponseEntity<Page<AddressDto>> advancedSearch(
            @Parameter(description = "City name (optional, partial match)") @RequestParam(required = false) String city,
            @Parameter(description = "Zip code (optional)") @RequestParam(required = false) String zipCode,
            @Parameter(description = "State/Province ID (optional)") @RequestParam(required = false) Integer stateProvinceId,
            @Parameter(description = "Page number (zero-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        // Delegate to service which builds dynamic filters using Specifications
        return ResponseEntity.ok(addressService.searchAddresses(city, zipCode, stateProvinceId, pageable));
    }

    @Operation(
            summary = "Get all addresses with sorting",
            description = "Retrieves a paginated list of all addresses with dynamic sorting. Example: ?sort=city,asc"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sorted list of addresses returned")
    })
    @GetMapping("/sorted")
    public ResponseEntity<Page<AddressDto>> getAllSorted(
            @Parameter(description = "Page number (0-based)")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Page size")
            @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "Sorting criteria, e.g., city,asc")
            @RequestParam(defaultValue = "addressId,asc") String[] sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(SortUtils.parseSorts(sort)));
        return ResponseEntity.ok(addressService.getAllAddresses(pageable));
    }

    @Operation(
            summary = "Search addresses with sorting",
            description = "Performs multi-field search with dynamic sorting. Example: ?sort=postalCode,desc"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sorted filtered results returned")
    })
    @GetMapping("/search-advanced/sorted")
    public ResponseEntity<Page<AddressDto>> searchWithSorting(
            @Parameter(description = "City filter (optional)")
            @RequestParam(required = false) String city,

            @Parameter(description = "Postal code filter (optional)")
            @RequestParam(required = false) String postalCode,

            @Parameter(description = "StateProvince ID filter (optional)")
            @RequestParam(required = false) Integer stateProvinceId,

            @Parameter(description = "Page number (0-based)")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Page size")
            @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "Sorting criteria, e.g., modifiedDate,desc")
            @RequestParam(defaultValue = "addressId,asc") String[] sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(SortUtils.parseSorts(sort)));
        return ResponseEntity.ok(addressService.searchAddresses(city, postalCode, stateProvinceId, pageable));
    }


}
