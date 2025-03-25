package com.grupo2.clinica.services;

import com.grupo2.clinica.dtos.EspecialidadDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EspecialidadService {
    Page<EspecialidadDTO> findAll(String search, Pageable pageable);
    Optional<EspecialidadDTO> findById(Long id);
    EspecialidadDTO save(EspecialidadDTO especialidad);
    Optional<EspecialidadDTO> update(Long id, EspecialidadDTO especialidad);
    Optional<EspecialidadDTO> deleteById(Long id);
}
