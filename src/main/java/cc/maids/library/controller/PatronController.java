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

}

