package com.example.backend.controller.production.productlistpricehistory;

import com.example.backend.dto.production.productlistpricehistory.ProductListPriceHistoryDto;
import com.example.backend.service.production.productlistpricehistory.ProductListPriceHistoryService;
import com.example.backend.util.response.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/api/product-list-price-history")
@RequiredArgsConstructor
@Tag(name = "Product List Price History", description = "Manage product pricing history")
public class ProductListPriceHistoryController {

    private final ProductListPriceHistoryService service;

    @GetMapping
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @Operation(summary = "Get all list price history")
    public ResponseEntity<List<ProductListPriceHistoryDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{productId}/{startDate}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ProductListPriceHistory found"),
            @ApiResponse(responseCode = "404", description = "ProductListPriceHistory not found")
    })
    @Operation(summary = "Get by ID")
    public ResponseEntity<ProductListPriceHistoryDto> getById(
            @PathVariable Integer productId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate) {
        return ResponseEntity.ok(service.getById(productId, startDate));
    }

    @PostMapping
    @Operation(summary = "Create a new price history record")
    @ApiResponse(responseCode = "200", description = "ProductListPriceHistory successfully created")
    public ResponseEntity<ProductListPriceHistoryDto> create(@RequestBody ProductListPriceHistoryDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{productId}/{startDate}")
    @Operation(summary = "Update price history record")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ProductListPriceHistory successfully updated"),
            @ApiResponse(responseCode = "404", description = "ProductListPriceHistory not found")
    })
    public ResponseEntity<ProductListPriceHistoryDto> update(
            @PathVariable Integer productId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestBody ProductListPriceHistoryDto dto) {
        return ResponseEntity.ok(service.update(productId, startDate, dto));
    }

    @DeleteMapping("/{productId}/{startDate}")
    @Operation(summary = "Delete price history record")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "ProductListPriceHistory successfully deleted"),
            @ApiResponse(responseCode = "404", description = "ProductListPriceHistory not found")
    })
    public ResponseEntity<Void> delete(
            @PathVariable Integer productId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate) {
        service.delete(productId, startDate);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paginated")
    @Operation(summary = "Get paginated list price history")
    @ApiResponse(responseCode = "200", description = "Page retrieved successfully")
    public ResponseEntity<PagedResponse<ProductListPriceHistoryDto>> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "listPrice,asc") String[] sort
    ) {
        Sort.Direction direction = sort.length > 1 && "desc".equalsIgnoreCase(sort[1])
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0]));
        return ResponseEntity.ok(service.getPaginated(pageable));
    }
}

