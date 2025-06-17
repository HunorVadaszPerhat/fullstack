package com.example.backend.controller.production.productinventory;

import com.example.backend.dto.production.productinventory.ProductInventoryDto;
import com.example.backend.service.production.productinventory.ProductInventoryService;
import com.example.backend.util.response.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("/api/product-inventories")
@RequiredArgsConstructor
public class ProductInventoryController {

    private final ProductInventoryService service;

    @Operation(summary = "Create inventory record", description = "Adds a new inventory record.")
    @ApiResponse(responseCode = "200", description = "Created successfully")
    @PostMapping
    public ResponseEntity<ProductInventoryDto> create(@RequestBody @Valid ProductInventoryDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Get inventory by ID", description = "Fetches an inventory record by composite key.")
    @ApiResponse(responseCode = "200", description = "Found successfully")
    @GetMapping("/{productId}/{locationId}")
    public ResponseEntity<ProductInventoryDto> getById(@PathVariable Integer productId, @PathVariable Integer locationId) {
        return ResponseEntity.ok(service.getById(productId, locationId));
    }

    @Operation(summary = "Get all inventories", description = "Returns all inventory records.")
    @ApiResponse(responseCode = "200", description = "List fetched successfully")
    @GetMapping
    public ResponseEntity<List<ProductInventoryDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Paginated fetch", description = "Paginated retrieval of inventory records.")
    @ApiResponse(responseCode = "200", description = "Page returned")
    @GetMapping("/paginated")
    public ResponseEntity<PagedResponse<ProductInventoryDto>> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "productId,asc") String[] sort) {

        String sortBy = sort[0];
        Sort.Direction direction = (sort.length > 1 && sort[1].equalsIgnoreCase("desc")) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return ResponseEntity.ok(service.getPaginated(pageable));
    }

    @Operation(summary = "Update inventory", description = "Updates an existing inventory record.")
    @ApiResponse(responseCode = "200", description = "Updated successfully")
    @PutMapping("/{productId}/{locationId}")
    public ResponseEntity<ProductInventoryDto> update(
            @PathVariable Integer productId,
            @PathVariable Integer locationId,
            @RequestBody @Valid ProductInventoryDto dto) {
        return ResponseEntity.ok(service.update(productId, locationId, dto));
    }

    @Operation(summary = "Delete inventory", description = "Deletes an inventory record by ID.")
    @ApiResponse(responseCode = "204", description = "Deleted successfully")
    @DeleteMapping("/{productId}/{locationId}")
    public ResponseEntity<Void> delete(@PathVariable Integer productId, @PathVariable Integer locationId) {
        service.delete(productId, locationId);
        return ResponseEntity.noContent().build();
    }
}
