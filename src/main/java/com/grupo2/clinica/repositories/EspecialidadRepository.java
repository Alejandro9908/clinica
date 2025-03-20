package com.grupo2.clinica.repositories;

import com.grupo2.clinica.dtos.EspecialidadDTO;
import com.grupo2.clinica.entities.Especialidad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EspecialidadRepository extends CrudRepository<Especialidad, Long> {
    @Query("SELECT new com.grupo2.clinica.dtos.EspecialidadDTO(e.id, e.descripcion, e.eliminado) FROM Especialidad e WHERE e.eliminado = false")
    Page<EspecialidadDTO> findAllEspecialidades(Pageable pageable);

    @Query("SELECT new com.grupo2.clinica.dtos.EspecialidadDTO(e.id, e.descripcion, e.eliminado) FROM Especialidad e WHERE e.id = :id AND e.eliminado = false")
    Optional<EspecialidadDTO> findByIdEspecialidad(@Param("id") Long id);
}
