package com.grupo2.clinica.services;

import com.grupo2.clinica.dtos.EspecialidadDTO;
import com.grupo2.clinica.entities.Especialidad;
import com.grupo2.clinica.repositories.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EspecialidadServiceImpl implements EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Override
    @Transactional (readOnly = true)
    public Page<EspecialidadDTO> findAll(String search, Pageable pageable) {
        return especialidadRepository.findAllEspecialidades(search, pageable);
    }

    @Override
    @Transactional (readOnly = true)
    public Optional<EspecialidadDTO> findById(Long id) {
        return especialidadRepository.findByIdEspecialidad(id);
    }

    @Override
    @Transactional
    public EspecialidadDTO save(EspecialidadDTO especialidadDTO) {
        Especialidad especialidad = convertToEntity(especialidadDTO);
        Especialidad saved = especialidadRepository.save(especialidad);
        return convertToDTO(saved);
    }

    @Override
    @Transactional
    public Optional<EspecialidadDTO> update(Long id, EspecialidadDTO especialidadDTO) {
        Optional<Especialidad> especialidadOptional = especialidadRepository.findById(id);

        if (especialidadOptional.isEmpty()) {
            return Optional.empty();
        }

        Especialidad especialidadUpdate = especialidadOptional.orElseThrow();
        especialidadUpdate.setDescripcion(especialidadDTO.getDescripcion());
        Especialidad updated = especialidadRepository.save(especialidadUpdate);

        return Optional.of(convertToDTO(updated));
    }


    @Override
    @Transactional
    public Optional<EspecialidadDTO> deleteById(Long id) {
        Optional<Especialidad> especialidadOptional = especialidadRepository.findById(id);

        if (especialidadOptional.isEmpty()) {
            return Optional.empty();
        }

        Especialidad especialidadDelete = especialidadOptional.orElseThrow();
        especialidadDelete.setEliminado(true);
        Especialidad updated = especialidadRepository.save(especialidadDelete);

        return Optional.of(convertToDTO(updated));
    }

    private Especialidad convertToEntity(EspecialidadDTO dto) {
        Especialidad especialidad = new Especialidad();
        especialidad.setDescripcion(dto.getDescripcion());
        especialidad.setEliminado(dto.getEliminado() != null ? dto.getEliminado() : false);
        return especialidad;
    }

    private EspecialidadDTO convertToDTO(Especialidad especialidad) {
        return new EspecialidadDTO(
                especialidad.getId(),
                especialidad.getDescripcion(),
                especialidad.getEliminado()
        );
    }
}
