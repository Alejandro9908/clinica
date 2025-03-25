package com.grupo2.clinica.services;

import com.grupo2.clinica.dtos.DoctorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface DoctorService {
    Page<DoctorDTO> findAll(String search, Pageable pageable);
    Optional<DoctorDTO> findById(Long id);
    DoctorDTO save(DoctorDTO doctorDTO);
    Optional<DoctorDTO> update(Long id, DoctorDTO doctorDTO);
    Optional<DoctorDTO> deleteById(Long id);
}
