package com.example.backend.controller.production.productdescription;

import com.example.backend.dto.production.productdescription.ProductDescriptionDto;
import com.example.backend.service.production.productdescription.ProductDescriptionService;
import com.example.backend.util.response.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-descriptions")
@RequiredArgsConstructor
@Tag(name = "ProductDescriptions", description = "Product description APIs")
public class ProductDescriptionController {

    private final ProductDescriptionService service;

    @Operation(summary = "Get all product descriptions", description = "Returns all product descriptions")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @GetMapping
    public ResponseEntity<List<ProductDescriptionDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get product description by ID", description = "Returns a product description by ID")
    @ApiResponse(responseCode = "200", description = "Product description retrieved")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDescriptionDto> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Create a new product description", description = "Adds a new product description")
    @ApiResponse(responseCode = "200", description = "Product description created")
    @PostMapping
    public ResponseEntity<ProductDescriptionDto> create(@RequestBody ProductDescriptionDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Update a product description", description = "Updates an existing product description")
    @ApiResponse(responseCode = "200", description = "Product description updated")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDescriptionDto> update(@PathVariable Integer id, @RequestBody ProductDescriptionDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Delete a product description", description = "Deletes a product description by ID")
    @ApiResponse(responseCode = "204", description = "Product description deleted")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get paginated product descriptions", description = "Returns paginated list of product descriptions")
    @ApiResponse(responseCode = "200", description = "Paginated product descriptions retrieved")
    @GetMapping("/paginated")
    public ResponseEntity<PagedResponse<ProductDescriptionDto>> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "description,asc") String[] sort
    ) {
        String sortBy = sort[0];
        Sort.Direction direction = sort.length > 1 && "desc".equalsIgnoreCase(sort[1])
                ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return ResponseEntity.ok(service.getPaginated(pageable));
    }
}

