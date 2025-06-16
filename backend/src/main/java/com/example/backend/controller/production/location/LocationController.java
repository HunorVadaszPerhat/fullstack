package com.example.backend.controller.production.location;

import com.example.backend.dto.production.location.LocationDto;
import com.example.backend.service.production.location.LocationService;
import com.example.backend.util.response.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("/api/locations")
@RequiredArgsConstructor
@Tag(name = "Location", description = "Location management APIs")
public class LocationController {

    private final LocationService service;

    @PostMapping
    @Operation(summary = "Create new location", description = "Stores a new location record.")
    @ApiResponse(responseCode = "200", description = "Location created successfully")
    public ResponseEntity<LocationDto> create(@Valid @RequestBody LocationDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get location by ID", description = "Fetch a single location by its ID.")
    @ApiResponse(responseCode = "200", description = "Fetched location by ID")
    public ResponseEntity<LocationDto> getById(@PathVariable Short id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    @Operation(summary = "Get all locations", description = "Returns all locations.")
    @ApiResponse(responseCode = "200", description = "Fetched all locations")
    public ResponseEntity<List<LocationDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update location", description = "Update an existing location.")
    @ApiResponse(responseCode = "200", description = "Location updated successfully")
    public ResponseEntity<LocationDto> update(@PathVariable Short id, @RequestBody @Valid LocationDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete location", description = "Deletes location by ID.")
    @ApiResponse(responseCode = "204", description = "Location deleted successfully")
    public ResponseEntity<Void> delete(@PathVariable Short id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paginated")
    @Operation(summary = "Paginated locations", description = "Paginated result with sort options.")
    @ApiResponse(responseCode = "200", description = "Fetched paginated locations")
    public ResponseEntity<PagedResponse<LocationDto>> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name,asc") String[] sort) {

        String sortBy = sort[0];
        Sort.Direction direction = sort.length > 1 && "desc".equalsIgnoreCase(sort[1])
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return ResponseEntity.ok(service.getPaginated(pageable));
    }
}
