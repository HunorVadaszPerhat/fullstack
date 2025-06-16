package com.example.backend.controller.production.productcategory;

import com.example.backend.dto.production.productcategory.ProductCategoryDto;
import com.example.backend.service.production.productcategory.ProductCategoryService;
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
@RequestMapping("/api/product-categories")
@RequiredArgsConstructor
@Tag(name = "ProductCategory", description = "Product category management APIs")
public class ProductCategoryController {

    private final ProductCategoryService service;

    @Operation(summary = "Get all product categories", description = "Returns a list of all product categories.")
    @ApiResponse(responseCode = "200", description = "Fetched all product categories")
    @GetMapping
    public ResponseEntity<List<ProductCategoryDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get product category by ID", description = "Returns a product category by its ID.")
    @ApiResponse(responseCode = "200", description = "Fetched product category by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProductCategoryDto> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Create product category", description = "Creates a new product category.")
    @ApiResponse(responseCode = "200", description = "Product category created successfully")
    @PostMapping
    public ResponseEntity<ProductCategoryDto> create(@RequestBody ProductCategoryDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Update product category", description = "Updates an existing product category by ID.")
    @ApiResponse(responseCode = "200", description = "Product category updated successfully")
    @PutMapping("/{id}")
    public ResponseEntity<ProductCategoryDto> update(@PathVariable Integer id, @RequestBody ProductCategoryDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Delete product category", description = "Deletes a product category by ID.")
    @ApiResponse(responseCode = "204", description = "Product category deleted successfully")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get paginated product categories", description = "Returns paginated result of product categories.")
    @ApiResponse(responseCode = "200", description = "Page retrieved successfully")
    @GetMapping("/paginated")
    public ResponseEntity<PagedResponse<ProductCategoryDto>> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name,asc") String[] sort
    ) {
        String sortBy = sort[0];
        Sort.Direction direction = sort.length > 1 && "desc".equalsIgnoreCase(sort[1])
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return ResponseEntity.ok(service.getPaginated(pageable));
    }
}
