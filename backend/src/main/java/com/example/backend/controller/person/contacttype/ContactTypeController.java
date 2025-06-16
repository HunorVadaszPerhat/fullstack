package com.example.backend.controller.person.contacttype;

import com.example.backend.dto.person.contacttype.ContactTypeDto;
import com.example.backend.service.person.contacttype.ContactTypeService;
import com.example.backend.util.response.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact-types")
@RequiredArgsConstructor
@Tag(name = "Contact Type", description = "Contact Type management APIs")
public class ContactTypeController {

    private final ContactTypeService contactTypeService;

    @Operation(summary = "Get all contact types", description = "Returns a list of all contact types.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @GetMapping
    public ResponseEntity<List<ContactTypeDto>> getAll() {
        return ResponseEntity.ok(contactTypeService.getAll());
    }

    @GetMapping("/paginated")
    @Operation(summary = "Get paginated contact types", description = "Returns a page of contact types with sorting options.")
    @ApiResponse(responseCode = "200", description = "Page retrieved successfully")
    public PagedResponse<ContactTypeDto> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "contactTypeId,asc") String[] sort
    ) {
        String sortBy = (sort.length > 0 && sort[0] != null && !sort[0].isBlank())
                ? sort[0]
                : "contactTypeId";

        Sort.Direction direction = (sort.length > 1 && "desc".equalsIgnoreCase(sort[1]))
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return contactTypeService.getPaginated(pageable);
    }

    @Operation(summary = "Get contact type by ID", description = "Returns a contact type for the given ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contact type found"),
            @ApiResponse(responseCode = "404", description = "Contact type not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ContactTypeDto> getById(
            @Parameter(description = "ID of the contact type") @PathVariable Integer id) {
        return ResponseEntity.ok(contactTypeService.getById(id));
    }

    @Operation(summary = "Create a new contact type", description = "Adds a new contact type.")
    @ApiResponse(responseCode = "200", description = "Contact type successfully created")
    @PostMapping
    public ResponseEntity<ContactTypeDto> create(
            @RequestBody @Valid ContactTypeDto dto) {
        return ResponseEntity.ok(contactTypeService.create(dto));
    }

    @Operation(summary = "Update contact type", description = "Updates an existing contact type by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contact type successfully updated"),
            @ApiResponse(responseCode = "404", description = "Contact type not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ContactTypeDto> update(
            @Parameter(description = "ID of the contact type to update") @PathVariable Integer id,
            @RequestBody @Valid ContactTypeDto dto) {
        return ResponseEntity.ok(contactTypeService.update(id, dto));
    }

    @Operation(summary = "Delete contact type", description = "Deletes a contact type by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Contact type successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Contact type not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the contact type to delete") @PathVariable Integer id) {
        contactTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

