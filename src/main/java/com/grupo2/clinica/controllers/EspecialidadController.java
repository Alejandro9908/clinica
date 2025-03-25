package com.grupo2.clinica.controllers;

import com.grupo2.clinica.dtos.EspecialidadDTO;
import com.grupo2.clinica.services.EspecialidadService;
import com.grupo2.clinica.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadController {

    @Autowired
    private EspecialidadService especialidadService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<EspecialidadDTO>>> index(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "descripcion") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) String search
    ) {
        // Se valida campo de ordenamiento
        List<String> allowedSortFields = Arrays.asList("id", "descripcion");

        if (!allowedSortFields.contains(sortField)) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(
                    400, "Campo de ordenamiento no válido: " + sortField, null
            ));
        }

        // Se valida dirección de ordenamiento
        Sort.Direction direction;
        try {
            direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(
                    400,
                    "Dirección de ordenamiento no válida: " + sortDirection + ". Usa 'asc' o 'desc'.",
                    null
            ));
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
        Page<EspecialidadDTO> especialidades = especialidadService.findAll(search, pageable);

        return ResponseEntity.ok(new ApiResponse<>(200, "Especialidades obtenidas con éxito", especialidades));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EspecialidadDTO>> show(@PathVariable Long id) {
        Optional<EspecialidadDTO> especialidadOptional = especialidadService.findById(id);
        if (especialidadOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "No se encontró la especialidad con el ID " + id, null));
        }
        return ResponseEntity.ok(new ApiResponse<>(200, "Especialidad encontrada", especialidadOptional.get()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EspecialidadDTO>> create(
            @Valid @RequestBody EspecialidadDTO especialidadDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return validationParams(bindingResult);
        }
        EspecialidadDTO created = especialidadService.save(especialidadDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, "Especialidad creada exitosamente", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EspecialidadDTO>> update(
            @Valid @RequestBody EspecialidadDTO especialidadDTO,
            BindingResult bindingResult,
            @PathVariable Long id
    ) {
        if (bindingResult.hasErrors()) {
            return validationParams(bindingResult);
        }
        Optional<EspecialidadDTO> especialidadOptional = especialidadService.update(id, especialidadDTO);
        if (especialidadOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "No se encontró la especialidad con el ID " + id, null));
        }
        return ResponseEntity.ok(new ApiResponse<>(200, "Especialidad actualizada exitosamente", especialidadOptional.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<EspecialidadDTO>> delete(@PathVariable Long id) {
        Optional<EspecialidadDTO> especialidadOptional = especialidadService.findById(id);
        if (especialidadOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "No se encontró la especialidad con el ID " + id, null));
        }
        especialidadService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Especialidad eliminada exitosamente", especialidadOptional.get()));
    }

    private ResponseEntity<ApiResponse<EspecialidadDTO>> validationParams(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        bindingResult.getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(400, "Error en la validación de los datos", null, errors));
    }
}
