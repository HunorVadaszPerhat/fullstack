package com.example.backend.controller.person.businessentitycontact;

import com.example.backend.dto.person.businessentitycontact.BusinessEntityContactDto;
import com.example.backend.service.person.businessentitycontact.BusinessEntityContactService;
import com.example.backend.util.response.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/business-entity-contacts")
@RequiredArgsConstructor
public class BusinessEntityContactController {

    private final BusinessEntityContactService service;

    @Operation(summary = "Create business entity contact", description = "Creates a new contact association for a business entity.")
    @ApiResponse(responseCode = "200", description = "Business entity contact successfully created")
    @PostMapping
    public ResponseEntity<BusinessEntityContactDto> create(
            @RequestBody @Valid BusinessEntityContactDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Get business entity contact by composite key", description = "Retrieves a contact association using businessEntityId, personId, and contactTypeId.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Business entity contact found"),
            @ApiResponse(responseCode = "404", description = "Business entity contact not found")
    })
    @GetMapping("/{businessEntityId}/{personId}/{contactTypeId}")
    public ResponseEntity<BusinessEntityContactDto> getById(
            @Parameter(description = "Business entity ID") @PathVariable Integer businessEntityId,
            @Parameter(description = "Person ID") @PathVariable Integer personId,
            @Parameter(description = "Contact type ID") @PathVariable Integer contactTypeId) {
        return ResponseEntity.ok(service.getById(businessEntityId, personId, contactTypeId));
    }

    @Operation(summary = "Get all business entity contacts", description = "Returns all contact associations for business entities.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @GetMapping
    public ResponseEntity<List<BusinessEntityContactDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get paginated business entity contacts", description = "Returns a page of business entity contact records with sorting options.")
    @ApiResponse(responseCode = "200", description = "Page retrieved successfully")
    @GetMapping("/paginated")
    public PagedResponse<BusinessEntityContactDto> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "businessEntityId,asc") String[] sort
    ) {
        String sortBy = (sort.length > 0 && sort[0] != null && !sort[0].isBlank())
                ? sort[0]
                : "businessEntityId";

        Sort.Direction direction = (sort.length > 1 && "desc".equalsIgnoreCase(sort[1]))
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return service.getPaginated(pageable);
    }

    @Operation(summary = "Update business entity contact", description = "Updates an existing contact association.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Business entity contact successfully updated"),
            @ApiResponse(responseCode = "404", description = "Business entity contact not found")
    })
    @PutMapping
    public ResponseEntity<BusinessEntityContactDto> update(
            @RequestBody @Valid BusinessEntityContactDto dto) {
        return ResponseEntity.ok(service.update(dto));
    }

    @Operation(summary = "Delete business entity contact", description = "Deletes the contact association using the composite key.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Business entity contact successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Business entity contact not found")
    })
    @DeleteMapping("/{businessEntityId}/{personId}/{contactTypeId}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Business entity ID") @PathVariable Integer businessEntityId,
            @Parameter(description = "Person ID") @PathVariable Integer personId,
            @Parameter(description = "Contact type ID") @PathVariable Integer contactTypeId) {
        service.delete(businessEntityId, personId, contactTypeId);
        return ResponseEntity.noContent().build();
    }
}

