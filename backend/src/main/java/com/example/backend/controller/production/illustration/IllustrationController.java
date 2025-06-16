package com.example.backend.controller.production.illustration;

import com.example.backend.dto.production.illustration.IllustrationDto;
import com.example.backend.service.production.illustration.IllustrationService;
import com.example.backend.util.response.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/illustrations")
@RequiredArgsConstructor
@Tag(name = "Illustration", description = "Illustration management APIs")
public class IllustrationController {

    private final IllustrationService service;

    @Operation(summary = "Get all illustrations")
    @ApiResponse(responseCode = "200", description = "Fetched all illustrations")
    @GetMapping
    public ResponseEntity<List<IllustrationDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get illustration by ID")
    @ApiResponse(responseCode = "200", description = "Fetched illustration by ID")
    @GetMapping("/{id}")
    public ResponseEntity<IllustrationDto> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Create a new illustration")
    @ApiResponse(responseCode = "201", description = "Created illustration")
    @PostMapping
    public ResponseEntity<IllustrationDto> create(@RequestBody @Valid IllustrationDto dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing illustration")
    @ApiResponse(responseCode = "200", description = "Updated illustration")
    @PutMapping("/{id}")
    public ResponseEntity<IllustrationDto> update(@PathVariable Integer id, @RequestBody @Valid IllustrationDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Delete an illustration")
    @ApiResponse(responseCode = "204", description = "Deleted illustration")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get paginated illustrations", description = "Returns paginated result of illustrations.")
    @ApiResponse(responseCode = "200", description = "Page retrieved successfully")
    @GetMapping("/paginated")
    public ResponseEntity<PagedResponse<IllustrationDto>> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "illustrationId,asc") String[] sort
    ) {
        String sortBy = sort[0];
        Sort.Direction direction = sort.length > 1 && "desc".equalsIgnoreCase(sort[1])
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return ResponseEntity.ok(service.getPaginated(pageable));
    }
}
