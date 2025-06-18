package com.example.backend.controller.production.productproductphoto;

import com.example.backend.domain.model.production.productproductphoto.ProductProductPhotoId;
import com.example.backend.dto.production.productproductphoto.ProductProductPhotoDto;
import com.example.backend.service.production.productproductphoto.ProductProductPhotoService;
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
@RequestMapping("/api/product-product-photos")
@RequiredArgsConstructor
public class ProductProductPhotoController {

    private final ProductProductPhotoService service;

    @Operation(summary = "Get all ProductProductPhotos")
    @ApiResponse(responseCode = "200", description = "List of ProductProductPhotos")
    @GetMapping
    public ResponseEntity<List<ProductProductPhotoDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get paginated ProductProductPhotos")
    @ApiResponse(responseCode = "200", description = "Page of ProductProductPhotos")
    @GetMapping("/paginated")
    public ResponseEntity<PagedResponse<ProductProductPhotoDto>> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "productId,asc") String[] sort) {

        String sortBy = sort[0];
        Sort.Direction direction = sort.length > 1 && "desc".equalsIgnoreCase(sort[1])
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<ProductProductPhotoDto> result = service.getPaginated(pageable);
        return ResponseEntity.ok(new PagedResponse<>(result));
    }

    @Operation(summary = "Get ProductProductPhoto by ID")
    @ApiResponse(responseCode = "200", description = "Fetched successfully")
    @GetMapping("/{productId}/{productPhotoId}")
    public ResponseEntity<ProductProductPhotoDto> getById(
            @PathVariable Integer productId,
            @PathVariable Integer productPhotoId) {
        return ResponseEntity.ok(service.getById(new ProductProductPhotoId(productId, productPhotoId)));
    }

    @Operation(summary = "Create ProductProductPhoto")
    @ApiResponse(responseCode = "200", description = "Created successfully")
    @PostMapping
    public ResponseEntity<ProductProductPhotoDto> create(@Valid @RequestBody ProductProductPhotoDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Update ProductProductPhoto")
    @ApiResponse(responseCode = "200", description = "Updated successfully")
    @PutMapping("/{productId}/{productPhotoId}")
    public ResponseEntity<ProductProductPhotoDto> update(
            @PathVariable Integer productId,
            @PathVariable Integer productPhotoId,
            @Valid @RequestBody ProductProductPhotoDto dto) {
        return ResponseEntity.ok(service.update(new ProductProductPhotoId(productId, productPhotoId), dto));
    }

    @Operation(summary = "Delete ProductProductPhoto")
    @ApiResponse(responseCode = "204", description = "Deleted successfully")
    @DeleteMapping("/{productId}/{productPhotoId}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer productId,
            @PathVariable Integer productPhotoId) {
        service.delete(new ProductProductPhotoId(productId, productPhotoId));
        return ResponseEntity.noContent().build();
    }
}

