package com.example.backend.controller.humanresources.employeepayhistory;

import com.example.backend.domain.model.humanresources.employeepayhistory.EmployeePayHistoryId;
import com.example.backend.dto.humanresources.employeepayhistory.EmployeePayHistoryDto;
import com.example.backend.service.humanresources.employeepayhistory.EmployeePayHistoryService;
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

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/employee-pay-histories")
@RequiredArgsConstructor
@Tag(name = "Employee Pay History", description = "Employee Pay History management APIs")
public class EmployeePayHistoryController {

    private final EmployeePayHistoryService service;

    @Operation(summary = "Get all pay histories")
    @GetMapping
    public ResponseEntity<List<EmployeePayHistoryDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get paginated pay histories")
    @GetMapping("/paginated")
    public PagedResponse<EmployeePayHistoryDto> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id.businessEntityId,asc") String[] sort) {
        String sortBy = sort[0];
        Sort.Direction direction = (sort.length > 1 && "desc".equalsIgnoreCase(sort[1])) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return service.getPaginated(pageable);
    }

    @Operation(summary = "Get by composite ID")
    @GetMapping("/{businessEntityId}/{rateChangeDate}")
    public ResponseEntity<EmployeePayHistoryDto> getById(
            @PathVariable Integer businessEntityId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime rateChangeDate) {
        return ResponseEntity.ok(service.getById(new EmployeePayHistoryId(businessEntityId, rateChangeDate)));
    }

    @Operation(summary = "Create new pay history")
    @PostMapping
    public ResponseEntity<EmployeePayHistoryDto> create(@RequestBody @Valid EmployeePayHistoryDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Update pay history")
    @PutMapping("/{businessEntityId}/{rateChangeDate}")
    public ResponseEntity<EmployeePayHistoryDto> update(
            @PathVariable Integer businessEntityId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime rateChangeDate,
            @RequestBody @Valid EmployeePayHistoryDto dto) {
        return ResponseEntity.ok(service.update(new EmployeePayHistoryId(businessEntityId, rateChangeDate), dto));
    }

    @Operation(summary = "Delete pay history")
    @DeleteMapping("/{businessEntityId}/{rateChangeDate}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer businessEntityId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime rateChangeDate) {
        service.delete(new EmployeePayHistoryId(businessEntityId, rateChangeDate));
        return ResponseEntity.noContent().build();
    }
}
