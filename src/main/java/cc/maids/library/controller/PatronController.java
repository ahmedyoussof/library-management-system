package cc.maids.library.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;

import cc.maids.library.dto.PatronDTO;
import cc.maids.library.model.Patron;
import cc.maids.library.service.PatronService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;


@RestController
@RequestMapping("/api/patrons")
@RequiredArgsConstructor
public class PatronController {

  private final PatronService patronService;

  private final ObjectMapper patronMapper;

  @PostMapping
  @Operation(
      summary = "Add a new patron",
      description = "Add a new patron to the system"
  )
  public ResponseEntity<PatronDTO> addPatron(@Valid @RequestBody PatronDTO patron) {
    Patron createdPatron = patronService.addPatron(patronMapper.convertValue(patron, Patron.class));
    return new ResponseEntity<>(patronMapper.convertValue(createdPatron, PatronDTO.class), HttpStatus.CREATED);
  }

  @GetMapping
  @Operation(
      summary = "Get all patrons",
      description = "Retrieve a list of all patrons"
  )
  public ResponseEntity<List<PatronDTO>> getAllPatrons() {

    List<PatronDTO> foundPatrons = patronService.getAllPatrons().stream()
        .map(patron -> patronMapper.convertValue(patron, PatronDTO.class)).toList();
    return ResponseEntity.ok(foundPatrons);
  }

  @GetMapping("/{id}")
  @Operation(
      summary = "Get a patron by ID",
      description = "Retrieve details of a specific patron by ID",
      parameters = {
          @Parameter(name = "id", description = "ID of the patron to retrieve", example = "1")
      }
  )
  public ResponseEntity<PatronDTO> getPatronById(@PathVariable Long id) {

    return ResponseEntity.ok(patronMapper.convertValue(patronService.getPatronById(id), PatronDTO.class));
  }

  @PutMapping("/{id}")
  @Operation(
      summary = "Update a patron",
      description = "Update an existing patron's information",
      parameters = {
          @Parameter(name = "id", description = "ID of the patron to be updated", example = "1")
      }
  )
  public ResponseEntity<PatronDTO> updatePatron(
      @Parameter(description = "ID of the patron to be updated", example = "1") @PathVariable Long id,
      @Valid @RequestBody PatronDTO patron) {

    Patron updatedPatron = patronService.updatePatron(id, patronMapper.convertValue(patron, Patron.class));
    return ResponseEntity.ok(patronMapper.convertValue(updatedPatron, PatronDTO.class));
  }

  @DeleteMapping("/{id}")
  @Operation(
      summary = "Delete a patron",
      description = "Remove a patron from the library",
      parameters = {
          @Parameter(name = "id", description = "ID of the patron to be deleted", example = "1")
      }
  )
  public ResponseEntity<Void> deletePatron(
      @Parameter(description = "ID of the patron to be deleted", example = "1") @PathVariable Long id) {
    patronService.deletePatron(id);
    return ResponseEntity.noContent().build();
  }

}

