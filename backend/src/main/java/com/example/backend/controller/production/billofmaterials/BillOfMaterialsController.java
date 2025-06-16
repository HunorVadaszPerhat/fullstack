package com.example.backend.controller.production.billofmaterials;

import com.example.backend.dto.production.billofmaterials.BillOfMaterialsDto;
import com.example.backend.service.production.billofmaterials.BillOfMaterialsService;
import com.example.backend.util.response.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bill-of-materials")
@RequiredArgsConstructor
@Tag(name = "Bill Of Materials", description = "Bill Of Materials management APIs")
public class BillOfMaterialsController {

    private final BillOfMaterialsService service;

    @Operation(summary = "Create a new bill of materials entry")
    @PostMapping
    public ResponseEntity<BillOfMaterialsDto> create(@Valid @RequestBody BillOfMaterialsDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Get bill of materials by ID")
    @GetMapping("/{id}")
    public ResponseEntity<BillOfMaterialsDto> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Get all bill of materials entries")
    @GetMapping
    public ResponseEntity<List<BillOfMaterialsDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get paginated bill of materials entries")
    @GetMapping("/paginated")
    public ResponseEntity<PagedResponse<BillOfMaterialsDto>> getPaginated(Pageable pageable) {
        return ResponseEntity.ok(service.getPaginated(pageable));
    }

    @Operation(summary = "Update bill of materials")
    @PutMapping("/{id}")
    public ResponseEntity<BillOfMaterialsDto> update(@PathVariable Integer id, @RequestBody BillOfMaterialsDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Delete bill of materials")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
