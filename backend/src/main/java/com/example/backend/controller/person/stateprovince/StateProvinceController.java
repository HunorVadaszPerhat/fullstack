package com.example.backend.controller.person.stateprovince;

import com.example.backend.dto.person.stateprovince.StateProvinceDto;
import com.example.backend.service.person.stateprovince.StateProvinceService;
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
@RequestMapping("/api/state-provinces")
@RequiredArgsConstructor
@Tag(name = "State Province", description = "State Province management APIs")
public class StateProvinceController {

    private final StateProvinceService stateProvinceService;

    @Operation(summary = "Create a state province", description = "Creates a new state/province linked to a country and territory.")
    @ApiResponse(responseCode = "200", description = "State province successfully created")
    @PostMapping
    public ResponseEntity<StateProvinceDto> create(
            @RequestBody @Valid StateProvinceDto dto) {
        return ResponseEntity.ok(stateProvinceService.create(dto));
    }

    @Operation(summary = "Get state province by ID", description = "Returns a state/province record by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "State province found"),
            @ApiResponse(responseCode = "404", description = "State province not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<StateProvinceDto> getById(
            @Parameter(description = "ID of the state/province") @PathVariable Integer id) {
        return ResponseEntity.ok(stateProvinceService.getById(id));
    }

    @Operation(summary = "Get all state provinces", description = "Returns all state/province records.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @GetMapping
    public ResponseEntity<List<StateProvinceDto>> getAll() {
        return ResponseEntity.ok(stateProvinceService.getAll());
    }

    @Operation(summary = "Get paginated state provinces", description = "Returns a paginated list of state/province records with sorting options.")
    @ApiResponse(responseCode = "200", description = "Page retrieved successfully")
    @GetMapping("/paginated")
    public PagedResponse<StateProvinceDto> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "stateProvinceId,asc") String[] sort
    ) {
        String sortBy = (sort.length > 0 && sort[0] != null && !sort[0].isBlank())
                ? sort[0]
                : "stateProvinceId";

        Sort.Direction direction = (sort.length > 1 && "desc".equalsIgnoreCase(sort[1]))
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return stateProvinceService.getPaginated(pageable);
    }

    @Operation(summary = "Update state province", description = "Updates an existing state/province record.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "State province successfully updated"),
            @ApiResponse(responseCode = "404", description = "State province not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<StateProvinceDto> update(
            @Parameter(description = "ID of the state/province to update") @PathVariable Integer id,
            @RequestBody @Valid StateProvinceDto dto) {
        return ResponseEntity.ok(stateProvinceService.update(id, dto));
    }

    @Operation(summary = "Delete state province", description = "Deletes a state/province record by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "State province successfully deleted"),
            @ApiResponse(responseCode = "404", description = "State province not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the state/province to delete") @PathVariable Integer id) {
        stateProvinceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
