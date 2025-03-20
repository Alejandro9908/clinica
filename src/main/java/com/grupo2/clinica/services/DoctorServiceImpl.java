package com.grupo2.clinica.services;

import com.grupo2.clinica.dtos.DoctorDTO;
import com.grupo2.clinica.entities.Doctor;
import com.grupo2.clinica.entities.Especialidad;
import com.grupo2.clinica.repositories.DoctorRepository;
import com.grupo2.clinica.repositories.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<DoctorDTO> findAll(Pageable pageable) {
        return doctorRepository.findAllDoctores(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DoctorDTO> findById(Long id) {
        return doctorRepository.findByIdDoctor(id);
    }

    @Override
    @Transactional
    public DoctorDTO save(DoctorDTO doctorDTO) {
        Especialidad especialidad = especialidadRepository.findById(doctorDTO.getEspecialidadId())
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));

        Doctor doctor = convertToEntity(doctorDTO);
        doctor.setEspecialidad(especialidad);
        Doctor saved = doctorRepository.save(doctor);
        return convertToDTO(saved);
    }

    @Override
    @Transactional
    public Optional<DoctorDTO> update(Long id, DoctorDTO doctorDTO) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);

        if (optionalDoctor.isEmpty()) {
            return Optional.empty();
        }

        Doctor doctor = optionalDoctor.get();
        doctor.setNombre(doctorDTO.getNombre());
        doctor.setApellido(doctorDTO.getApellido());
        doctor.setDireccion(doctorDTO.getDireccion());
        doctor.setTelefono(doctorDTO.getTelefono());
        doctor.setCorreo(doctorDTO.getCorreo());
        doctor.setEstado(doctorDTO.getEstado());
        doctor.setFechaNacimiento(doctorDTO.getFechaNacimiento());

        if (doctorDTO.getEspecialidadId() != null) {
            Especialidad especialidad = especialidadRepository.findById(doctorDTO.getEspecialidadId())
                    .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
            doctor.setEspecialidad(especialidad);
        }

        Doctor updated = doctorRepository.save(doctor);
        return Optional.of(convertToDTO(updated));
    }

    @Override
    @Transactional
    public Optional<DoctorDTO> deleteById(Long id) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);

        if (optionalDoctor.isEmpty()) {
            return Optional.empty();
        }

        Doctor doctorDelete = optionalDoctor.get();
        doctorDelete.setEliminado(true);
        Doctor updated = doctorRepository.save(doctorDelete);
        return Optional.of(convertToDTO(updated));
    }

    private DoctorDTO convertToDTO(Doctor doctor) {
        return new DoctorDTO(
                doctor.getId(),
                doctor.getNombre(),
                doctor.getApellido(),
                doctor.getDireccion(),
                doctor.getTelefono(),
                doctor.getCorreo(),
                doctor.getEstado(),
                doctor.getFechaNacimiento(),
                doctor.getEspecialidad().getId()
        );
    }

    private Doctor convertToEntity(DoctorDTO doctorDTO) {
        Doctor doctor = new Doctor();
        doctor.setNombre(doctorDTO.getNombre());
        doctor.setApellido(doctorDTO.getApellido());
        doctor.setDireccion(doctorDTO.getDireccion());
        doctor.setTelefono(doctorDTO.getTelefono());
        doctor.setCorreo(doctorDTO.getCorreo());
        doctor.setEstado(doctorDTO.getEstado());
        doctor.setFechaNacimiento(doctorDTO.getFechaNacimiento());
        return doctor;
    }
}
