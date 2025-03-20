package com.grupo2.clinica.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EspecialidadDTO {

    private Long id;

    @NotBlank()
    @Size(min = 1, max = 50)
    private String descripcion;

    private Boolean eliminado;

    public EspecialidadDTO() {
    }

    public EspecialidadDTO(Long id, String descripcion, Boolean eliminado) {
        this.id = id;
        this.descripcion = descripcion;
        this.eliminado = eliminado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }
}
