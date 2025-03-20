package com.grupo2.clinica.services;

import com.grupo2.clinica.dtos.PacienteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PacienteService {
    Page<PacienteDTO> findAll(Pageable pageable);
    Optional<PacienteDTO> findById(Long id);
    PacienteDTO save(PacienteDTO doctorDTO);
    Optional<PacienteDTO> update(Long id, PacienteDTO doctorDTO);
    Optional<PacienteDTO> deleteById(Long id);
}
