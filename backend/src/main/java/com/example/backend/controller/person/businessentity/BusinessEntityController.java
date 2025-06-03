package com.example.backend.controller.person.businessentity;

import com.example.backend.dto.person.businessentity.BusinessEntityDto;
import com.example.backend.service.person.businessentity.BusinessEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/business-entities")
@RequiredArgsConstructor
public class BusinessEntityController {

    private final BusinessEntityService businessEntityService;

    @Operation(summary = "Create a new business entity", description = "Adds a new business entity record.")
    @ApiResponse(responseCode = "200", description = "Business entity successfully created")
    @PostMapping
    public ResponseEntity<BusinessEntityDto> create(
            @RequestBody @Valid BusinessEntityDto dto) {
        return ResponseEntity.ok(businessEntityService.create(dto));
    }

    @Operation(summary = "Get business entity by ID", description = "Returns a business entity for the given ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Business entity found"),
            @ApiResponse(responseCode = "404", description = "Business entity not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BusinessEntityDto> getById(
            @Parameter(description = "ID of the business entity") @PathVariable Integer id) {
        return ResponseEntity.ok(businessEntityService.getById(id));
    }

    @Operation(summary = "Get all business entities", description = "Returns a list of all business entities.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @GetMapping
    public ResponseEntity<List<BusinessEntityDto>> getAll() {
        return ResponseEntity.ok(businessEntityService.getAll());
    }

    @Operation(summary = "Update business entity", description = "Updates an existing business entity by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Business entity successfully updated"),
            @ApiResponse(responseCode = "404", description = "Business entity not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BusinessEntityDto> update(
            @Parameter(description = "ID of the business entity to update") @PathVariable Integer id,
            @RequestBody @Valid BusinessEntityDto dto) {
        return ResponseEntity.ok(businessEntityService.update(id, dto));
    }

    @Operation(summary = "Delete business entity", description = "Deletes a business entity by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Business entity successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Business entity not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the business entity to delete") @PathVariable Integer id) {
        businessEntityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}