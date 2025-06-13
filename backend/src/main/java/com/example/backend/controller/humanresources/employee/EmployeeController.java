package com.example.backend.controller.humanresources.employee;

import com.example.backend.dto.humanresources.employee.EmployeeDto;
import com.example.backend.service.humanresources.employee.EmployeeService;
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
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @Operation(summary = "Get all employees", description = "Returns a list of all employees.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get paginated employees", description = "Returns a page of employees with sorting options.")
    @ApiResponse(responseCode = "200", description = "Page retrieved successfully")
    @GetMapping("/paginated")
    public PagedResponse<EmployeeDto> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "businessEntityId,asc") String[] sort
    ) {
        String sortBy = (sort.length > 0 && sort[0] != null && !sort[0].isBlank()) ? sort[0] : "businessEntityId";
        Sort.Direction direction = (sort.length > 1 && "desc".equalsIgnoreCase(sort[1]))
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return service.getPaginated(pageable);
    }

    @Operation(summary = "Get employee by ID", description = "Returns an employee by BusinessEntityID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee found"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Create a new employee", description = "Adds a new employee.")
    @ApiResponse(responseCode = "200", description = "Employee successfully created")
    @PostMapping
    public ResponseEntity<EmployeeDto> create(@RequestBody @Valid EmployeeDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Update employee", description = "Updates an existing employee identified by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee successfully updated"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> update(@PathVariable Integer id, @RequestBody @Valid EmployeeDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Attempt to delete employee", description = "Simulates the DB trigger that blocks deletion.")
    @ApiResponses({
            @ApiResponse(responseCode = "500", description = "Employees cannot be deleted")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id); // throws UnsupportedOperationException
        return ResponseEntity.noContent().build();
    }
}

