package com.esfe.Asistencia.Servicios.Interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esfe.Asistencia.Modelos.Estudiante;

public interface IEstudianteService {

    Page<Estudiante> buscarTodos(Pageable pageable);

    List<Estudiante > obtenerTodos();

    Optional<Estudiante> buscarPorId(Integer id);

    Estudiante crearOeditar(Estudiante estudiante);

    void eliminarPorId(Integer id);
}
