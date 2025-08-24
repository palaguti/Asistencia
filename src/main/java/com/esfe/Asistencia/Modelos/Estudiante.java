package com.esfe.Asistencia.Modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.*;

@Entity
@Table(name = "estudiantes")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

     @NotBlank(message = "El nombre es requerido")
    private String nombre;

    @NotBlank(message = "El apellido es requerido")
    private String apellido;

     @NotBlank(message = "El teléfono es requerido")
    private String telefono;

    @NotBlank(message = "El codigo de estudiante es requerido")
    private String codigo_estudiante;

        @ManyToMany
       @JoinTable(name = "estudiante_grupo",
           joinColumns = @JoinColumn(name = "estudiante_id"),
           inverseJoinColumns = @JoinColumn(name = "grupo_id")
       )

       private Set<Grupo> grupos = new HashSet<>();

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
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
    
    public String getTelefono() {
      return telefono;
      }
    
    public void setTelefono(String telefono) {
          this.telefono = telefono;
     }
    
    public String getCodigo_estudiante() {
         return codigo_estudiante;
         }
    
     public void setCodigo_estudiante(String codigo_estudiante) {
        this.codigo_estudiante = codigo_estudiante;
         }
    
}