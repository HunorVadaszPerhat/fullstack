package com.example.backend.controller.humanresources.employeedepartmenthistory;

import com.example.backend.domain.model.humanresources.employeedepartmenthistory.EmployeeDepartmentHistoryId;
import com.example.backend.dto.humanresources.employeedepartmenthistory.EmployeeDepartmentHistoryDto;
import com.example.backend.service.humanresources.employeedepartmenthistory.EmployeeDepartmentHistoryService;
import com.example.backend.util.response.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/employee-department-history")
@RequiredArgsConstructor
@Tag(name = "Employee Department History", description = "Employee Department History management APIs")
public class EmployeeDepartmentHistoryController {

    private final EmployeeDepartmentHistoryService service;

    @Operation(summary = "Get all records")
    @GetMapping
    public ResponseEntity<List<EmployeeDepartmentHistoryDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get paginated records")
    @GetMapping("/paginated")
    public PagedResponse<EmployeeDepartmentHistoryDto> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id.businessEntityId,asc") String[] sort
    ) {
        Sort.Direction direction = (sort.length > 1 && "desc".equalsIgnoreCase(sort[1]))
                ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0]));
        return service.getPaginated(pageable);
    }

    @Operation(summary = "Get by composite ID")
    @GetMapping("/{businessEntityId}/{startDate}/{departmentId}/{shiftId}")
    public ResponseEntity<EmployeeDepartmentHistoryDto> getById(
            @PathVariable Integer businessEntityId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @PathVariable Short departmentId,
            @PathVariable Byte shiftId
    ) {
        var id = new EmployeeDepartmentHistoryId(businessEntityId, startDate, departmentId, shiftId);
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Create new record")
    @PostMapping
    public ResponseEntity<EmployeeDepartmentHistoryDto> create(@RequestBody @Valid EmployeeDepartmentHistoryDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Update existing record")
    @PutMapping("/{businessEntityId}/{startDate}/{departmentId}/{shiftId}")
    public ResponseEntity<EmployeeDepartmentHistoryDto> update(
            @PathVariable Integer businessEntityId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @PathVariable Short departmentId,
            @PathVariable Byte shiftId,
            @RequestBody @Valid EmployeeDepartmentHistoryDto dto
    ) {
        var id = new EmployeeDepartmentHistoryId(businessEntityId, startDate, departmentId, shiftId);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Delete record")
    @DeleteMapping("/{businessEntityId}/{startDate}/{departmentId}/{shiftId}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer businessEntityId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @PathVariable Short departmentId,
            @PathVariable Byte shiftId
    ) {
        var id = new EmployeeDepartmentHistoryId(businessEntityId, startDate, departmentId, shiftId);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

