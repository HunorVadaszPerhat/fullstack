package com.example.backend.controller.production.productphoto;

import com.example.backend.dto.production.productphoto.ProductPhotoDto;
import com.example.backend.service.production.productphoto.ProductPhotoService;
import com.example.backend.util.response.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-photos")
@RequiredArgsConstructor
public class ProductPhotoController {

    private final ProductPhotoService service;

    @Operation(summary = "Create product photo")
    @ApiResponse(responseCode = "200", description = "Successfully created")
    @PostMapping
    public ResponseEntity<ProductPhotoDto> create(@Valid @RequestBody ProductPhotoDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Get product photo by ID")
    @ApiResponse(responseCode = "200", description = "Successfully fetched")
    @GetMapping("/{id}")
    public ResponseEntity<ProductPhotoDto> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Get all product photos")
    @ApiResponse(responseCode = "200", description = "Successfully fetched all")
    @GetMapping
    public ResponseEntity<List<ProductPhotoDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get paginated product photos", description = "Returns paginated result of product photos")
    @ApiResponse(responseCode = "200", description = "Page retrieved successfully")
    @GetMapping("/paginated")
    public ResponseEntity<PagedResponse<ProductPhotoDto>> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "productPhotoId,asc") String[] sort
    ) {
        Sort.Direction direction = sort.length > 1 && "desc".equalsIgnoreCase(sort[1])
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0]));
        return ResponseEntity.ok(service.getPaginated(pageable));
    }

    @Operation(summary = "Update product photo")
    @ApiResponse(responseCode = "200", description = "Successfully updated")
    @PutMapping("/{id}")
    public ResponseEntity<ProductPhotoDto> update(@PathVariable Integer id, @Valid @RequestBody ProductPhotoDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Delete product photo")
    @ApiResponse(responseCode = "204", description = "Successfully deleted")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
