package com.esfe.Asistencia.Repositorios;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import com.esfe.Asistencia.Modelos.EstudianteGrupo;

public interface IEstudianteGrupoRepository extends JpaRepository<EstudianteGrupo, Integer> {
    Page<EstudianteGrupo> findByOrderByEstudianteDesc(Pageable pageable);

}
