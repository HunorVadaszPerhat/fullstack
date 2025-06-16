package com.example.backend.controller.person.password;

import com.example.backend.dto.person.password.PasswordDto;
import com.example.backend.service.person.password.PasswordService;
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
@RequestMapping("/api/passwords")
@RequiredArgsConstructor
@Tag(name = "Password", description = "Password management APIs")
public class PasswordController {

    private final PasswordService passwordService;

    @Operation(summary = "Get all passwords", description = "Returns a list of all password records.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @GetMapping
    public ResponseEntity<List<PasswordDto>> getAll() {
        return ResponseEntity.ok(passwordService.getAll());
    }

    @GetMapping("/paginated")
    @Operation(summary = "Get paginated passwords", description = "Returns a page of password records with sorting options.")
    @ApiResponse(responseCode = "200", description = "Page retrieved successfully")
    public PagedResponse<PasswordDto> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "personId,asc") String[] sort
    ) {
        String sortBy = (sort.length > 0 && sort[0] != null && !sort[0].isBlank())
                ? sort[0]
                : "personId";

        Sort.Direction direction = (sort.length > 1 && "desc".equalsIgnoreCase(sort[1]))
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return passwordService.getPaginated(pageable);
    }

    @Operation(summary = "Get password by person ID", description = "Returns the password record for a given person.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password found"),
            @ApiResponse(responseCode = "404", description = "Password not found")
    })
    @GetMapping("/{personId}")
    public ResponseEntity<PasswordDto> getByPersonId(
            @Parameter(description = "Person ID (BusinessEntityID)") @PathVariable Integer personId) {
        return ResponseEntity.ok(passwordService.getById(personId));
    }

    @Operation(summary = "Create a password", description = "Adds a new password record for a person.")
    @ApiResponse(responseCode = "200", description = "Password successfully created")
    @PostMapping
    public ResponseEntity<PasswordDto> create(
            @RequestBody @Valid PasswordDto dto) {
        return ResponseEntity.ok(passwordService.create(dto));
    }

    @Operation(summary = "Update password", description = "Updates an existing password record for a person.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password successfully updated"),
            @ApiResponse(responseCode = "404", description = "Password not found")
    })
    @PutMapping("/{personId}")
    public ResponseEntity<PasswordDto> update(
            @Parameter(description = "Person ID (BusinessEntityID) to update") @PathVariable Integer personId,
            @RequestBody @Valid PasswordDto dto) {
        return ResponseEntity.ok(passwordService.update(personId, dto));
    }

    @Operation(summary = "Delete password", description = "Deletes a password record by person ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Password successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Password not found")
    })
    @DeleteMapping("/{personId}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Person ID (BusinessEntityID) to delete") @PathVariable Integer personId) {
        passwordService.delete(personId);
        return ResponseEntity.noContent().build();
    }
}
