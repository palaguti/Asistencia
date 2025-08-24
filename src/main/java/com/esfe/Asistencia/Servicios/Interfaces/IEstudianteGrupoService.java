package com.esfe.Asistencia.Servicios.Interfaces;

import org.springframework.data.domain.*;
import java.util.List;
import com.esfe.Asistencia.Modelos.EstudianteGrupo;

public interface IEstudianteGrupoService {

    List<EstudianteGrupo> obtenerTodos();

    Page<EstudianteGrupo> buscarTodosPaginados(Pageable pageable);

    EstudianteGrupo buscarPorId(Integer id);

    EstudianteGrupo crearOeditar(EstudianteGrupo estudianteGrupo);

    void eliminarPorId(Integer id);

}
