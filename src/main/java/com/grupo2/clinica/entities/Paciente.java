package com.grupo2.clinica.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "paciente")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    @NotBlank
    @Size(min = 1, max = 50)
    private String nombre;

    @Column(length = 50, nullable = false)
    @NotBlank
    @Size(min = 1, max = 50)
    private String apellido;

    @Column(length = 100, nullable = false)
    @NotBlank
    @Size(min = 1, max = 100)
    private String direccion;

    @Column(length = 20, nullable = false)
    @NotBlank
    @Size(min = 1, max = 20)
    private String telefono;

    @Column(length = 50, nullable = false)
    @NotBlank
    @Size(min = 1, max = 50)
    private String correo;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @Column(length = 20, nullable = false)
    @NotBlank
    @Size(min = 1, max = 20)
    private String dpi;

    @Column(nullable = false)
    private Boolean alfabeta;

    @Column(length = 20)
    @Size(min = 1, max = 20)
    private String estadoCivil;

    @Column(length = 1)
    @NotBlank
    @Size(min = 1, max = 1)
    private String genero;

    @Column(nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'activo'")
    private String estado = "activo";

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean eliminado = false;

    public Paciente() {
    }

    public Paciente(
            Long id,
            String nombre,
            String apellido,
            String direccion,
            String telefono,
            String correo,
            Date fechaNacimiento,
            String dpi,
            Boolean alfabeta,
            String estadoCivil,
            String genero,
            String estado,
            Boolean eliminado
    ) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.dpi = dpi;
        this.alfabeta = alfabeta;
        this.estadoCivil = estadoCivil;
        this.genero = genero;
        this.estado = estado;
        this.eliminado = eliminado;
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

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public Boolean getAlfabeta() {
        return alfabeta;
    }

    public void setAlfabeta(Boolean alfabeta) {
        this.alfabeta = alfabeta;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }
}
