package com.example.backend.controller.person.emailaddress;

import com.example.backend.dto.person.emailaddress.EmailAddressDto;
import com.example.backend.dto.person.emailaddress.EmailAddressIdDto;
import com.example.backend.service.person.emailaddress.EmailAddressService;
import com.example.backend.util.response.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/email-addresses")
@RequiredArgsConstructor
public class EmailAddressController {

    private final EmailAddressService emailAddressService;

    @Operation(summary = "Get all email addresses", description = "Returns a list of all email addresses.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @GetMapping
    public ResponseEntity<List<EmailAddressDto>> getAll() {
        return ResponseEntity.ok(emailAddressService.getAll());
    }

    @Operation(summary = "Get paginated email addresses", description = "Returns a page of email addresses with sorting options.")
    @ApiResponse(responseCode = "200", description = "Page retrieved successfully")
    @GetMapping("/paginated")
    public ResponseEntity<PagedResponse<EmailAddressDto>> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id.businessEntityId,asc") String[] sort
    ) {
        String sortBy = (sort.length > 0 && sort[0] != null && !sort[0].isBlank()) ? sort[0] : "id.businessEntityId";
        Sort.Direction direction = (sort.length > 1 && "desc".equalsIgnoreCase(sort[1])) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        PagedResponse<EmailAddressDto> result = emailAddressService.getPaginated(pageable);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Get email address by composite ID", description = "Returns an email address by business entity ID and email address ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Email address found"),
            @ApiResponse(responseCode = "404", description = "Email address not found")
    })
    @GetMapping("/{businessEntityId}/{emailAddressId}")
    public ResponseEntity<EmailAddressDto> getById(
            @PathVariable Integer businessEntityId,
            @PathVariable Integer emailAddressId) {

        EmailAddressIdDto id = new EmailAddressIdDto(businessEntityId, emailAddressId);
        return ResponseEntity.ok(emailAddressService.getById(id));
    }

    @Operation(summary = "Create a new email address", description = "Adds a new email address for a person.")
    @ApiResponse(responseCode = "200", description = "Email address successfully created")
    @PostMapping
    public ResponseEntity<EmailAddressDto> create(
            @RequestBody @Valid EmailAddressDto dto) {
        return ResponseEntity.ok(emailAddressService.create(dto));
    }

    @Operation(summary = "Update email address", description = "Updates an existing email address identified by composite ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Email address successfully updated"),
            @ApiResponse(responseCode = "404", description = "Email address not found")
    })
    @PutMapping("/{businessEntityId}/{emailAddressId}")
    public ResponseEntity<EmailAddressDto> update(
            @PathVariable Integer businessEntityId,
            @PathVariable Integer emailAddressId,
            @RequestBody @Valid EmailAddressDto dto) {

        EmailAddressIdDto id = new EmailAddressIdDto(businessEntityId, emailAddressId);
        return ResponseEntity.ok(emailAddressService.update(id, dto));
    }

    @Operation(summary = "Delete email address", description = "Deletes an email address by composite ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Email address successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Email address not found")
    })
    @DeleteMapping("/{businessEntityId}/{emailAddressId}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer businessEntityId,
            @PathVariable Integer emailAddressId) {

        EmailAddressIdDto id = new EmailAddressIdDto(businessEntityId, emailAddressId);
        emailAddressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
