package com.example.backend.controller.person.personphone;

import com.example.backend.dto.person.personphone.PersonPhoneDto;
import com.example.backend.service.person.personphone.PersonPhoneService;
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
@RequestMapping("/api/person-phones")
@RequiredArgsConstructor
@Tag(name = "Person Phone", description = "Person Phone management APIs")
public class PersonPhoneController {

    private final PersonPhoneService personPhoneService;

    @Operation(summary = "Create person phone", description = "Creates a new person phone entry for a given person and phone number type.")
    @ApiResponse(responseCode = "200", description = "Person phone successfully created")
    @PostMapping
    public ResponseEntity<PersonPhoneDto> create(
            @RequestBody @Valid PersonPhoneDto dto) {
        return ResponseEntity.ok(personPhoneService.create(dto));
    }

    @Operation(summary = "Get person phone by composite key", description = "Returns a person phone record by businessEntityId, phoneNumber, and phoneNumberTypeId.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Person phone found"),
            @ApiResponse(responseCode = "404", description = "Person phone not found")
    })
    @GetMapping("/{businessEntityId}/{phoneNumber}/{phoneNumberTypeId}")
    public ResponseEntity<PersonPhoneDto> getById(
            @Parameter(description = "Business entity ID") @PathVariable Integer businessEntityId,
            @Parameter(description = "Phone number") @PathVariable String phoneNumber,
            @Parameter(description = "Phone number type ID") @PathVariable Integer phoneNumberTypeId) {
        return ResponseEntity.ok(personPhoneService.getById(businessEntityId, phoneNumber, phoneNumberTypeId));
    }

    @Operation(summary = "Get all person phones", description = "Returns all person phone entries.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @GetMapping
    public ResponseEntity<List<PersonPhoneDto>> getAll() {
        return ResponseEntity.ok(personPhoneService.getAll());
    }

    @Operation(summary = "Get paginated person phones", description = "Returns a page of person phone records with sorting options.")
    @ApiResponse(responseCode = "200", description = "Page retrieved successfully")
    @GetMapping("/paginated")
    public PagedResponse<PersonPhoneDto> getPaginated(
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
        return personPhoneService.getPaginated(pageable);
    }

    @Operation(summary = "Update person phone", description = "Updates an existing person phone record.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Person phone successfully updated"),
            @ApiResponse(responseCode = "404", description = "Person phone not found")
    })
    @PutMapping
    public ResponseEntity<PersonPhoneDto> update(
            @RequestBody @Valid PersonPhoneDto dto) {
        return ResponseEntity.ok(personPhoneService.update(dto));
    }

    @Operation(summary = "Delete person phone", description = "Deletes a person phone record by composite key.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Person phone successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Person phone not found")
    })
    @DeleteMapping("/{businessEntityId}/{phoneNumber}/{phoneNumberTypeId}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Business entity ID") @PathVariable Integer businessEntityId,
            @Parameter(description = "Phone number") @PathVariable String phoneNumber,
            @Parameter(description = "Phone number type ID") @PathVariable Integer phoneNumberTypeId) {
        personPhoneService.delete(businessEntityId, phoneNumber, phoneNumberTypeId);
        return ResponseEntity.noContent().build();
    }
}
