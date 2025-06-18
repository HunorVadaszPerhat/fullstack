package com.example.backend.controller.production.productmodelillustration;

import com.example.backend.dto.production.productmodelillustration.ProductModelIllustrationDto;
import com.example.backend.service.production.productmodelillustration.ProductModelIllustrationService;
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
@RequestMapping("/api/product-model-illustrations")
@RequiredArgsConstructor
@Tag(name = "ProductModelIllustration", description = "Manage product-model and illustration relationships")
public class ProductModelIllustrationController {

    private final ProductModelIllustrationService service;

    @GetMapping
    @Operation(summary = "Get all")
    @ApiResponse(responseCode = "200", description = "Fetched all successfully")
    public ResponseEntity<List<ProductModelIllustrationDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{productModelId}/{illustrationId}")
    @Operation(summary = "Get by ID")
    @ApiResponse(responseCode = "200", description = "Fetched by ID successfully")
    public ResponseEntity<ProductModelIllustrationDto> getById(
            @PathVariable Integer productModelId,
            @PathVariable Integer illustrationId) {
        return ResponseEntity.ok(service.getById(productModelId, illustrationId));
    }

    @PostMapping
    @Operation(summary = "Create new mapping")
    @ApiResponse(responseCode = "200", description = "Created successfully")
    public ResponseEntity<ProductModelIllustrationDto> create(@RequestBody ProductModelIllustrationDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{productModelId}/{illustrationId}")
    @Operation(summary = "Update existing mapping")
    @ApiResponse(responseCode = "200", description = "Updated successfully")
    public ResponseEntity<ProductModelIllustrationDto> update(
            @PathVariable Integer productModelId,
            @PathVariable Integer illustrationId,
            @RequestBody ProductModelIllustrationDto dto) {
        return ResponseEntity.ok(service.update(productModelId, illustrationId, dto));
    }

    @DeleteMapping("/{productModelId}/{illustrationId}")
    @Operation(summary = "Delete mapping")
    @ApiResponse(responseCode = "204", description = "Deleted successfully")
    public ResponseEntity<Void> delete(
            @PathVariable Integer productModelId,
            @PathVariable Integer illustrationId) {
        service.delete(productModelId, illustrationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paginated")
    @Operation(summary = "Get paginated")
    @ApiResponse(responseCode = "200", description = "Paginated result")
    public ResponseEntity<PagedResponse<ProductModelIllustrationDto>> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id.productModelId,asc") String[] sort
    ) {
        Sort.Direction direction = sort.length > 1 && "desc".equalsIgnoreCase(sort[1])
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0]));
        return ResponseEntity.ok(service.getPaginated(pageable));
    }
}

