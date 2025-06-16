package com.example.backend.controller.production.productcosthistory;

import com.example.backend.dto.production.productcosthistory.ProductCostHistoryDto;
import com.example.backend.service.production.productcosthistory.ProductCostHistoryService;
import com.example.backend.util.response.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/product-cost-history")
@RequiredArgsConstructor
@Tag(name = "ProductCostHistory", description = "Product cost history management APIs")
public class ProductCostHistoryController {

    private final ProductCostHistoryService service;

    @Operation(summary = "Get all product cost history", description = "Returns all records")
    @ApiResponse(responseCode = "200", description = "Fetched all records")
    @GetMapping
    public ResponseEntity<List<ProductCostHistoryDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get by ID", description = "Returns a specific record")
    @ApiResponse(responseCode = "200", description = "Fetched by ID")
    @GetMapping("/{productId}/{startDate}")
    public ResponseEntity<ProductCostHistoryDto> getById(
            @PathVariable Integer productId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate
    ) {
        return ResponseEntity.ok(service.getById(productId, startDate));
    }

    @Operation(summary = "Create", description = "Creates a new product cost history")
    @ApiResponse(responseCode = "200", description = "Created successfully")
    @PostMapping
    public ResponseEntity<ProductCostHistoryDto> create(@RequestBody ProductCostHistoryDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Update", description = "Updates a product cost history")
    @ApiResponse(responseCode = "200", description = "Updated successfully")
    @PutMapping("/{productId}/{startDate}")
    public ResponseEntity<ProductCostHistoryDto> update(
            @PathVariable Integer productId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestBody ProductCostHistoryDto dto
    ) {
        return ResponseEntity.ok(service.update(productId, startDate, dto));
    }

    @Operation(summary = "Delete", description = "Deletes a product cost history")
    @ApiResponse(responseCode = "204", description = "Deleted successfully")
    @DeleteMapping("/{productId}/{startDate}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer productId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate
    ) {
        service.delete(productId, startDate);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Paginated", description = "Returns paginated product cost history")
    @ApiResponse(responseCode = "200", description = "Page fetched")
    @GetMapping("/paginated")
    public ResponseEntity<PagedResponse<ProductCostHistoryDto>> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "standardCost,asc") String[] sort
    ) {
        Sort.Direction direction = sort.length > 1 && "desc".equalsIgnoreCase(sort[1])
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0]));
        return ResponseEntity.ok(service.getPaginated(pageable));
    }
}

