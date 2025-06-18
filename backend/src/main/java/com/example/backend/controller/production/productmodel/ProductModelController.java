package com.example.backend.controller.production.productmodel;

import com.example.backend.dto.production.productmodel.ProductModelDto;
import com.example.backend.service.production.productmodel.ProductModelService;
import com.example.backend.util.response.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-models")
@RequiredArgsConstructor
@Tag(name = "Product Models", description = "Manage product models")
public class ProductModelController {

    private final ProductModelService service;

    @GetMapping
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @Operation(summary = "Get all product models")
    public ResponseEntity<List<ProductModelDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product model by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ProductModel found"),
            @ApiResponse(responseCode = "404", description = "ProductModel not found")
    })
    public ResponseEntity<ProductModelDto> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new product model")
    @ApiResponse(responseCode = "200", description = "ProductModel successfully created")
    public ResponseEntity<ProductModelDto> create(@RequestBody ProductModelDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product model")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ProductModel successfully updated"),
            @ApiResponse(responseCode = "404", description = "ProductModel not found")
    })
    public ResponseEntity<ProductModelDto> update(@PathVariable Integer id, @RequestBody ProductModelDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "ProductModel successfully deleted"),
            @ApiResponse(responseCode = "404", description = "ProductModel not found")
    })
    @Operation(summary = "Delete a product model")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paginated")
    @Operation(summary = "Get paginated product models")
    @ApiResponse(responseCode = "200", description = "Page retrieved successfully")
    public ResponseEntity<PagedResponse<ProductModelDto>> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name,asc") String[] sort
    ) {
        String sortBy = sort[0];
        Sort.Direction direction = (sort.length > 1 && "desc".equalsIgnoreCase(sort[1]))
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return ResponseEntity.ok(service.getPaginated(pageable));
    }
}

