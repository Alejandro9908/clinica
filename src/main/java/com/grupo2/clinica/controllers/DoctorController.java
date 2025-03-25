package com.grupo2.clinica.controllers;

import com.grupo2.clinica.dtos.DoctorDTO;
import com.grupo2.clinica.utils.ApiResponse;
import com.grupo2.clinica.services.DoctorService;
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
@RequestMapping("/api/doctores")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<DoctorDTO>>> index(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) String search
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
        Page<DoctorDTO> doctores = doctorService.findAll(search, pageable);
        return ResponseEntity.ok(new ApiResponse<>(200, "Lista de doctores obtenida", doctores));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorDTO>> show(@PathVariable Long id) {
        Optional<DoctorDTO> doctorOptional = doctorService.findById(id);
        if (doctorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "No se encontró registro con ID " + id, null));
        }
        return ResponseEntity.ok(new ApiResponse<>(200, "Registro encontrado", doctorOptional.get()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DoctorDTO>> create(
            @Valid @RequestBody DoctorDTO doctorDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return validationParams(bindingResult);
        }

        DoctorDTO created = doctorService.save(doctorDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, "Registro creado exitosamente", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorDTO>> update(
            @Valid @RequestBody DoctorDTO doctorDTO,
            BindingResult bindingResult,
            @PathVariable Long id
    ) {
        if (bindingResult.hasErrors()) {
            return validationParams(bindingResult);
        }

        Optional<DoctorDTO> doctorOptional = doctorService.update(id, doctorDTO);
        if (doctorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "No se encontró registro con ID " + id, null));
        }
        return ResponseEntity.ok(new ApiResponse<>(200, "Registro actualizado exitosamente", doctorOptional.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorDTO>> delete(@PathVariable Long id) {
        Optional<DoctorDTO> doctorOptional = doctorService.deleteById(id);
        if (doctorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "No se encontró registro con ID " + id, null));
        }
        doctorService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Especialidad eliminada exitosamente", doctorOptional.get()));
    }

    private ResponseEntity<ApiResponse<DoctorDTO>> validationParams(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(fieldError ->
                errors.put(fieldError.getField(), fieldError.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(400, "Error en la validación de los datos", errors));
    }
}
