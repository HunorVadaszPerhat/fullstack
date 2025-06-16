package com.example.backend.controller.production.document;

import com.example.backend.dto.production.document.DocumentDto;
import com.example.backend.service.production.document.DocumentService;
import com.example.backend.util.response.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/documents")
@RequiredArgsConstructor
@Tag(name = "Document", description = "Document management APIs")
public class DocumentController {

    private final DocumentService service;

    @Operation(summary = "Create new document", description = "Adds a new document to the system.")
    @ApiResponse(responseCode = "200", description = "Document successfully created")
    @PostMapping
    public ResponseEntity<DocumentDto> create(@RequestBody @Valid DocumentDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Get document by ID", description = "Fetches a document using its documentNode (hierarchy ID).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Document found"),
            @ApiResponse(responseCode = "404", description = "Document not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DocumentDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Get all documents", description = "Returns a list of all documents.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @GetMapping
    public ResponseEntity<List<DocumentDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get paginated documents", description = "Returns paginated result of documents.")
    @ApiResponse(responseCode = "200", description = "Page retrieved successfully")
    @GetMapping("/paginated")
    public ResponseEntity<PagedResponse<DocumentDto>> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title,asc") String[] sort
    ) {
        String sortBy = sort[0];
        Sort.Direction direction = sort.length > 1 && "desc".equalsIgnoreCase(sort[1])
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return ResponseEntity.ok(service.getPaginated(pageable));
    }


    @Operation(summary = "Update document", description = "Updates an existing document identified by documentNode.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Document successfully updated"),
            @ApiResponse(responseCode = "404", description = "Document not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DocumentDto> update(@PathVariable String id, @RequestBody @Valid DocumentDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Delete document", description = "Deletes a document by its documentNode.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Document successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Document not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

