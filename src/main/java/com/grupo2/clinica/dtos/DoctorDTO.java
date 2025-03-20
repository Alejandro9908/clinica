package com.grupo2.clinica.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class DoctorDTO {
    private Long id;

    @NotBlank
    @Size(min = 2, max = 50)
    private String nombre;

    @NotBlank
    @Size(min = 2, max = 50)
    private String apellido;

    @NotBlank
    @Size(min = 1, max = 100)
    private String direccion;

    @Size(min = 1, max = 20)
    private String telefono;

    @Size(min = 1, max = 50)
    private String correo;

    @Size(min = 1, max = 10)
    private String estado;

    @NotNull
    private Date fechaNacimiento;

    private Long especialidadId; // ID de la especialidad a la que pertenece el doctor

    public DoctorDTO() {
    }

    public DoctorDTO(Long id, String nombre, String apellido, String direccion, String telefono, String correo, String estado, Date fechaNacimiento, Long especialidadId) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.estado = estado;
        this.fechaNacimiento = fechaNacimiento;
        this.especialidadId = especialidadId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Long getEspecialidadId() {
        return especialidadId;
    }

    public void setEspecialidadId(Long especialidadId) {
        this.especialidadId = especialidadId;
    }
}

