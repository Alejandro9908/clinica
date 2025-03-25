package com.grupo2.clinica.repositories;

import com.grupo2.clinica.dtos.PacienteDTO;
import com.grupo2.clinica.entities.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends CrudRepository<Paciente, Long> {
    @Query("""
        SELECT new com.grupo2.clinica.dtos.PacienteDTO(
            p.id, p.nombre, p.apellido, p.direccion, p.telefono, p.correo,
            p.fechaNacimiento, p.dpi, p.alfabeta, p.estadoCivil, p.genero, p.estado
        )
        FROM Paciente p
        WHERE p.eliminado = false
        AND (
            :search IS NULL OR
            LOWER(CONCAT(p.nombre, ' ', p.apellido)) LIKE LOWER(CONCAT('%', :search, '%')) OR
            LOWER(p.correo) LIKE LOWER(CONCAT('%', :search, '%')) OR
            p.dpi LIKE CONCAT('%', :search, '%')
        )
    """)
    Page<PacienteDTO> findAllPacientes(@Param("search") String search, Pageable pageable);

    @Query("SELECT new com.grupo2.clinica.dtos.PacienteDTO" +
            "(p.id, p.nombre, p.apellido, p.direccion, p.telefono, p.correo, p.fechaNacimiento, " +
            "p.dpi, p.alfabeta, p.estadoCivil, p.genero, p.estado) FROM Paciente p WHERE p.id = :id AND p.eliminado = false")
    Optional<PacienteDTO> findByIdPaciente(@Param("id") Long id);
}
