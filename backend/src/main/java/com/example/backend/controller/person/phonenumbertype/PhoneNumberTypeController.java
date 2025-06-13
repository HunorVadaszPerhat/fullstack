package com.example.backend.controller.person.phonenumbertype;

import com.example.backend.dto.person.phonenumbertype.PhoneNumberTypeDto;
import com.example.backend.service.person.phonenumbertype.PhoneNumberTypeService;
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
@RequestMapping("/api/phone-number-types")
@RequiredArgsConstructor
public class PhoneNumberTypeController {

    private final PhoneNumberTypeService phoneNumberTypeService;

    @Operation(summary = "Get all phone number types", description = "Returns a list of all phone number types.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @GetMapping
    public ResponseEntity<List<PhoneNumberTypeDto>> getAll() {
        return ResponseEntity.ok(phoneNumberTypeService.getAll());
    }

    @GetMapping("/paginated")
    @Operation(summary = "Get paginated phone number types", description = "Returns a page of phone number types with sorting options.")
    @ApiResponse(responseCode = "200", description = "Page retrieved successfully")
    public PagedResponse<PhoneNumberTypeDto> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "phoneNumberTypeId,asc") String[] sort
    ) {
        String sortBy = (sort.length > 0 && sort[0] != null && !sort[0].isBlank())
                ? sort[0]
                : "phoneNumberTypeId";

        Sort.Direction direction = (sort.length > 1 && "desc".equalsIgnoreCase(sort[1]))
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return phoneNumberTypeService.getPaginated(pageable);
    }

    @Operation(summary = "Get phone number type by ID", description = "Returns a phone number type by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Phone number type found"),
            @ApiResponse(responseCode = "404", description = "Phone number type not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PhoneNumberTypeDto> getById(
            @Parameter(description = "ID of the phone number type") @PathVariable Integer id) {
        return ResponseEntity.ok(phoneNumberTypeService.getById(id));
    }

    @Operation(summary = "Create phone number type", description = "Adds a new phone number type.")
    @ApiResponse(responseCode = "200", description = "Phone number type successfully created")
    @PostMapping
    public ResponseEntity<PhoneNumberTypeDto> create(
            @RequestBody @Valid PhoneNumberTypeDto dto) {
        return ResponseEntity.ok(phoneNumberTypeService.create(dto));
    }

    @Operation(summary = "Update phone number type", description = "Updates a phone number type by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Phone number type successfully updated"),
            @ApiResponse(responseCode = "404", description = "Phone number type not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PhoneNumberTypeDto> update(
            @Parameter(description = "ID of the phone number type to update") @PathVariable Integer id,
            @RequestBody @Valid PhoneNumberTypeDto dto) {
        return ResponseEntity.ok(phoneNumberTypeService.update(id, dto));
    }

    @Operation(summary = "Delete phone number type", description = "Deletes a phone number type by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Phone number type successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Phone number type not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the phone number type to delete") @PathVariable Integer id) {
        phoneNumberTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

