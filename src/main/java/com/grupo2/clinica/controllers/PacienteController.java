package com.grupo2.clinica.controllers;

import com.grupo2.clinica.dtos.PacienteDTO;
import com.grupo2.clinica.services.PacienteService;
import com.grupo2.clinica.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<PacienteDTO>>> index(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
        Page<PacienteDTO> pacientes = pacienteService.findAll(pageable);
        return ResponseEntity.ok(new ApiResponse<>(200, "Lista de pacientes obtenida", pacientes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PacienteDTO>> show(@PathVariable Long id) {
        Optional<PacienteDTO> pacienteOptional = pacienteService.findById(id);
        if (pacienteOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "No se encontró registro con ID " + id, null));
        }
        return ResponseEntity.ok(new ApiResponse<>(200, "Registro encontrado", pacienteOptional.get()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PacienteDTO>> create(
            @Valid @RequestBody PacienteDTO pacienteDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return validationParams(bindingResult);
        }

        PacienteDTO created = pacienteService.save(pacienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, "Registro creado exitosamente", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PacienteDTO>> update(
            @Valid @RequestBody PacienteDTO pacienteDTO,
            BindingResult bindingResult,
            @PathVariable Long id
    ) {
        if (bindingResult.hasErrors()) {
            return validationParams(bindingResult);
        }

        Optional<PacienteDTO> pacienteOptional = pacienteService.update(id, pacienteDTO);
        if (pacienteOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "No se encontró registro con ID " + id, null));
        }
        return ResponseEntity.ok(new ApiResponse<>(200, "Registro actualizado exitosamente", pacienteOptional.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<PacienteDTO>> delete(@PathVariable Long id) {
        Optional<PacienteDTO> pacienteOptional = pacienteService.deleteById(id);
        if (pacienteOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "No se encontró registro con ID " + id, null));
        }
        pacienteService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Especialidad eliminada exitosamente", pacienteOptional.get()));
    }

    private ResponseEntity<ApiResponse<PacienteDTO>> validationParams(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(fieldError ->
                errors.put(fieldError.getField(), fieldError.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(400, "Error en la validación de los datos", errors));
    }
}
