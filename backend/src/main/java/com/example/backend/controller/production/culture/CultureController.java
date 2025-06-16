package com.example.backend.controller.production.culture;

import com.example.backend.dto.production.culture.CultureDto;
import com.example.backend.service.production.culture.CultureService;
import com.example.backend.util.response.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("/api/cultures")
@RequiredArgsConstructor
@Tag(name = "Culture", description = "Culture management APIs")
public class CultureController {

    private final CultureService service;

    @GetMapping
    @Operation(summary = "Get all cultures", description = "Returns a list of all cultures.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved")
    public ResponseEntity<List<CultureDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/paginated")
    @Operation(summary = "Get paginated cultures", description = "Returns paginated result of cultures.")
    public ResponseEntity<PagedResponse<CultureDto>> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name,asc") String[] sort
    ) {
        String sortBy = sort[0];
        Sort.Direction direction = sort.length > 1 && "desc".equalsIgnoreCase(sort[1])
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return ResponseEntity.ok(service.getPaginated(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get culture by ID")
    public ResponseEntity<CultureDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create culture")
    public ResponseEntity<CultureDto> create(@RequestBody @Valid CultureDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update culture")
    public ResponseEntity<CultureDto> update(@PathVariable String id, @RequestBody @Valid CultureDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete culture")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
