package com.grupo2.clinica.services;

import com.grupo2.clinica.dtos.PacienteDTO;
import com.grupo2.clinica.entities.Paciente;
import com.grupo2.clinica.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PacienteServiceImpl implements PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<PacienteDTO> findAll(String search, Pageable pageable) {
        return pacienteRepository.findAllPacientes(search, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PacienteDTO> findById(Long id) {
        return pacienteRepository.findByIdPaciente(id);
    }

    @Override
    @Transactional
    public PacienteDTO save(PacienteDTO pacienteDTO) {
        Paciente paciente = convertToEntity(pacienteDTO);
        paciente.setEstado("activo");
        paciente.setEliminado(false);
        Paciente saved = pacienteRepository.save(paciente);
        return convertToDTO(saved);
    }

    @Override
    @Transactional
    public Optional<PacienteDTO> update(Long id, PacienteDTO pacienteDTO) {
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(id);

        if (optionalPaciente.isEmpty()) {
            return Optional.empty();
        }

        Paciente paciente = optionalPaciente.get();
        paciente.setNombre(pacienteDTO.getNombre());
        paciente.setApellido(pacienteDTO.getApellido());
        paciente.setDireccion(pacienteDTO.getDireccion());
        paciente.setTelefono(pacienteDTO.getTelefono());
        paciente.setCorreo(pacienteDTO.getCorreo());
        paciente.setFechaNacimiento(pacienteDTO.getFechaNacimiento());
        paciente.setDpi(pacienteDTO.getDpi());
        paciente.setAlfabeta(pacienteDTO.getAlfabeta());
        paciente.setEstadoCivil(pacienteDTO.getEstadoCivil());
        paciente.setGenero(pacienteDTO.getGenero());
        paciente.setEstado(pacienteDTO.getEstado());

        Paciente updated = pacienteRepository.save(paciente);
        return Optional.of(convertToDTO(updated));
    }

    @Override
    @Transactional
    public Optional<PacienteDTO> deleteById(Long id) {
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(id);

        if (optionalPaciente.isEmpty()) {
            return Optional.empty();
        }

        Paciente pacienteDelete = optionalPaciente.get();
        pacienteDelete.setEliminado(true);
        Paciente updated = pacienteRepository.save(pacienteDelete);
        return Optional.of(convertToDTO(updated));
    }

    private PacienteDTO convertToDTO(Paciente paciente) {
        return new PacienteDTO(
                paciente.getId(),
                paciente.getNombre(),
                paciente.getApellido(),
                paciente.getDireccion(),
                paciente.getTelefono(),
                paciente.getCorreo(),
                paciente.getFechaNacimiento(),
                paciente.getDpi(),
                paciente.getAlfabeta(),
                paciente.getEstadoCivil(),
                paciente.getGenero(),
                paciente.getEstado()
        );
    }

    private Paciente convertToEntity(PacienteDTO pacienteDTO) {
        Paciente paciente = new Paciente();
        paciente.setNombre(pacienteDTO.getNombre());
        paciente.setApellido(pacienteDTO.getApellido());
        paciente.setDireccion(pacienteDTO.getDireccion());
        paciente.setTelefono(pacienteDTO.getTelefono());
        paciente.setCorreo(pacienteDTO.getCorreo());
        paciente.setFechaNacimiento(pacienteDTO.getFechaNacimiento());
        paciente.setDpi(pacienteDTO.getDpi());
        paciente.setAlfabeta(pacienteDTO.getAlfabeta());
        paciente.setEstadoCivil(pacienteDTO.getEstadoCivil());
        paciente.setGenero(pacienteDTO.getGenero());
        paciente.setEstado(pacienteDTO.getEstado());
        return paciente;
    }
}
