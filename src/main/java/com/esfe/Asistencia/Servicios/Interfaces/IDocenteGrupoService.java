package com.esfe.Asistencia.Servicios.Interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

import com.esfe.Asistencia.Modelos.DocenteGrupo;

public interface IDocenteGrupoService {

    List<DocenteGrupo> obtenerTodos();

    Page<DocenteGrupo> buscarTodosPaginados(Pageable pageable);

    DocenteGrupo buscarPorId(Integer id);

    DocenteGrupo crearOeditar(DocenteGrupo docenteGrupo);

    void eliminarPorId(Integer id);
}
