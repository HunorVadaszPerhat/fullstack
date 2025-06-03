package com.example.backend.controller.person.person;

import com.example.backend.dto.person.person.PersonDto;
import com.example.backend.service.person.person.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @Operation(summary = "Create a new person", description = "Adds a new person with linked business entity.")
    @ApiResponse(responseCode = "200", description = "Person successfully created")
    @PostMapping
    public ResponseEntity<PersonDto> create(
            @RequestBody @Valid PersonDto dto) {
        return ResponseEntity.ok(personService.create(dto));
    }

    @Operation(summary = "Get person by ID", description = "Returns a person for the given ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Person found"),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getById(
            @Parameter(description = "ID of the person") @PathVariable Integer id) {
        return ResponseEntity.ok(personService.getById(id));
    }

    @Operation(summary = "Get all persons", description = "Returns a list of all persons.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @GetMapping
    public ResponseEntity<List<PersonDto>> getAll() {
        return ResponseEntity.ok(personService.getAll());
    }

    @Operation(summary = "Update person", description = "Updates a person by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Person successfully updated"),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PersonDto> update(
            @Parameter(description = "ID of the person to update") @PathVariable Integer id,
            @RequestBody @Valid PersonDto dto) {
        return ResponseEntity.ok(personService.update(id, dto));
    }

    @Operation(summary = "Delete person", description = "Deletes a person by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Person successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the person to delete") @PathVariable Integer id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
