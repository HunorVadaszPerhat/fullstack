package com.example.backend.controller.humanresources.shift;


import com.example.backend.dto.humanresources.shift.ShiftDto;
import com.example.backend.service.humanresources.shift.ShiftService;
import com.example.backend.util.response.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shifts")
@RequiredArgsConstructor
@Tag(name = "Shift", description = "Shift management APIs")
public class ShiftController {

    private final ShiftService shiftService;

    @Operation(summary = "Get all shifts", description = "Returns a list of all shifts.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @GetMapping
    public ResponseEntity<List<ShiftDto>> getAll() {
        return ResponseEntity.ok(shiftService.getAll());
    }

    @Operation(summary = "Get paginated shifts", description = "Returns a paginated list of shifts.")
    @ApiResponse(responseCode = "200", description = "Page retrieved successfully")
    @GetMapping("/paginated")
    public PagedResponse<ShiftDto> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "shiftId,asc") String[] sort
    ) {
        String sortBy = sort.length > 0 ? sort[0] : "shiftId";
        Sort.Direction direction = (sort.length > 1 && "desc".equalsIgnoreCase(sort[1]))
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return shiftService.getPaginated(pageable);
    }

    @Operation(summary = "Get shift by ID", description = "Returns a shift by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Shift found"),
            @ApiResponse(responseCode = "404", description = "Shift not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ShiftDto> getById(@PathVariable Byte id) {
        return ResponseEntity.ok(shiftService.getById(id));
    }

    @Operation(summary = "Create new shift", description = "Creates a new shift.")
    @ApiResponse(responseCode = "200", description = "Shift successfully created")
    @PostMapping
    public ResponseEntity<ShiftDto> create(@RequestBody ShiftDto dto) {
        return ResponseEntity.ok(shiftService.create(dto));
    }

    @Operation(summary = "Update shift", description = "Updates an existing shift by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Shift successfully updated"),
            @ApiResponse(responseCode = "404", description = "Shift not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ShiftDto> update(@PathVariable Byte id, @RequestBody ShiftDto dto) {
        return ResponseEntity.ok(shiftService.update(id, dto));
    }

    @Operation(summary = "Delete shift", description = "Deletes a shift by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Shift successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Shift not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Byte id) {
        shiftService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
