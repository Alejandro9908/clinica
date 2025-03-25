package com.grupo2.clinica.repositories;

import com.grupo2.clinica.dtos.DoctorDTO;
import com.grupo2.clinica.dtos.EspecialidadDTO;
import com.grupo2.clinica.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Long> {

    @Query("""
        SELECT new com.grupo2.clinica.dtos.DoctorDTO(
            d.id, d.nombre, d.apellido, d.direccion, d.telefono,
            d.correo, d.estado, d.fechaNacimiento, d.especialidad.id
        )
        FROM Doctor d
        WHERE d.eliminado = false
        AND (
            :search IS NULL OR
            LOWER(CONCAT(d.nombre, ' ', d.apellido)) LIKE LOWER(CONCAT('%', :search, '%')) OR
            LOWER(d.correo) LIKE LOWER(CONCAT('%', :search, '%'))
        )
    """)
    Page<DoctorDTO> findAllDoctores(@Param("search") String search, Pageable pageable);

    @Query("SELECT new com.grupo2.clinica.dtos.DoctorDTO" +
            "(d.id, d.nombre, d.apellido, d.direccion, d.telefono, d.correo, d.estado, d.fechaNacimiento, d.especialidad.id) " +
            "FROM Doctor d WHERE d.id = :id AND d.eliminado = false")
    Optional<DoctorDTO> findByIdDoctor(@Param("id") Long id);

}