package com.example.backend.controller.person.countryregion;

import com.example.backend.dto.person.countryregion.CountryRegionDto;
import com.example.backend.service.person.countryregion.CountryRegionService;
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
@RequestMapping("/api/country-regions")
@RequiredArgsConstructor
public class CountryRegionController {

    private final CountryRegionService countryRegionService;

    @Operation(summary = "Get all country regions", description = "Returns a list of all country and region records.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @GetMapping
    public ResponseEntity<List<CountryRegionDto>> getAll() {
        return ResponseEntity.ok(countryRegionService.getAll());
    }

    @GetMapping("/paginated")
    @Operation(summary = "Get paginated country regions", description = "Returns a page of country and region records with sorting options.")
    @ApiResponse(responseCode = "200", description = "Page retrieved successfully")
    public PagedResponse<CountryRegionDto> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "countryRegionCode,asc") String[] sort
    ) {
        String sortBy = (sort.length > 0 && sort[0] != null && !sort[0].isBlank())
                ? sort[0]
                : "countryRegionCode";

        Sort.Direction direction = (sort.length > 1 && "desc".equalsIgnoreCase(sort[1]))
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return countryRegionService.getPaginated(pageable);
    }

    @Operation(summary = "Get country region by code", description = "Returns a country or region by its ISO code.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Country region found"),
            @ApiResponse(responseCode = "404", description = "Country region not found")
    })
    @GetMapping("/{code}")
    public ResponseEntity<CountryRegionDto> getByCode(
            @Parameter(description = "ISO code of the country or region") @PathVariable String code) {
        return ResponseEntity.ok(countryRegionService.getById(code));
    }

    @Operation(summary = "Create a new country region", description = "Adds a new country or region record.")
    @ApiResponse(responseCode = "200", description = "Country region successfully created")
    @PostMapping
    public ResponseEntity<CountryRegionDto> create(
            @RequestBody @Valid CountryRegionDto dto) {
        return ResponseEntity.ok(countryRegionService.create(dto));
    }

    @Operation(summary = "Update country region", description = "Updates an existing country or region by its code.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Country region successfully updated"),
            @ApiResponse(responseCode = "404", description = "Country region not found")
    })
    @PutMapping("/{code}")
    public ResponseEntity<CountryRegionDto> update(
            @Parameter(description = "ISO code of the country or region to update") @PathVariable String code,
            @RequestBody @Valid CountryRegionDto dto) {
        return ResponseEntity.ok(countryRegionService.update(code, dto));
    }

    @Operation(summary = "Delete country region", description = "Deletes a country or region by its ISO code.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Country region successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Country region not found")
    })
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ISO code of the country or region to delete") @PathVariable String code) {
        countryRegionService.delete(code);
        return ResponseEntity.noContent().build();
    }
}

