package com.example.backend.controller.production.productmodelproductdescriptionculture;

import com.example.backend.domain.model.production.productmodelproductdescriptionculture.ProductModelProductDescriptionCultureId;
import com.example.backend.dto.production.productmodelproductdescriptionculture.ProductModelProductDescriptionCultureDto;
import com.example.backend.service.production.productmodelproductdescriptionculture.ProductModelProductDescriptionCultureService;
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
@RequestMapping("/api/product-model-description-cultures")
@RequiredArgsConstructor
public class ProductModelProductDescriptionCultureController {

    private final ProductModelProductDescriptionCultureService service;

    @Operation(summary = "Create association", description = "Creates a new product model-description-culture link")
    @ApiResponse(responseCode = "200", description = "Created successfully")
    @PostMapping
    public ResponseEntity<ProductModelProductDescriptionCultureDto> create(
            @RequestBody @Valid ProductModelProductDescriptionCultureDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Get by ID")
    @ApiResponse(responseCode = "200", description = "Fetched successfully")
    @GetMapping("/{modelId}/{descId}/{cultureId}")
    public ResponseEntity<ProductModelProductDescriptionCultureDto> getById(
            @PathVariable Integer modelId,
            @PathVariable Integer descId,
            @PathVariable String cultureId) {
        return ResponseEntity.ok(service.getById(
                new ProductModelProductDescriptionCultureId(modelId, descId, cultureId)
        ));
    }

    @Operation(summary = "List all")
    @ApiResponse(responseCode = "200", description = "List fetched successfully")
    @GetMapping
    public ResponseEntity<List<ProductModelProductDescriptionCultureDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Paginated list")
    @ApiResponse(responseCode = "200", description = "Paged result fetched")
    @GetMapping("/paginated")
    public ResponseEntity<PagedResponse<ProductModelProductDescriptionCultureDto>> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "modifiedDate,desc") String[] sort) {

        String sortBy = (sort.length > 0 && sort[0] != null && !sort[0].isBlank())
                ? sort[0]
                : "addressId";

        Sort.Direction direction = (sort.length > 1 && "desc".equalsIgnoreCase(sort[1]))
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return ResponseEntity.ok(service.getPaginated(pageable));
    }

    @Operation(summary = "Delete by ID")
    @ApiResponse(responseCode = "204", description = "Deleted successfully")
    @DeleteMapping("/{modelId}/{descId}/{cultureId}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer modelId,
            @PathVariable Integer descId,
            @PathVariable String cultureId) {
        service.delete(new ProductModelProductDescriptionCultureId(modelId, descId, cultureId));
        return ResponseEntity.noContent().build();
    }
}

