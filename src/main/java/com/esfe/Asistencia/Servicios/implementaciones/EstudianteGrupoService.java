package com.esfe.Asistencia.Servicios.implementaciones;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.esfe.Asistencia.Modelos.EstudianteGrupo;
import com.esfe.Asistencia.Repositorios.IEstudianteGrupoRepository;
import com.esfe.Asistencia.Servicios.Interfaces.IEstudianteGrupoService;

@Service
public class EstudianteGrupoService implements IEstudianteGrupoService {

    @Autowired
    private IEstudianteGrupoRepository estudianteGrupoRepository;

    @Override
    public Page<EstudianteGrupo> buscarTodosPaginados(Pageable pageable) {
        return estudianteGrupoRepository.findAll(pageable);

    }

    @Override
    public List<EstudianteGrupo> obtenerTodos() {
        return estudianteGrupoRepository.findAll();
    }

    @Override
    public EstudianteGrupo buscarPorId(Integer id) {
        return estudianteGrupoRepository.findById(id).orElse(null);
    }

    @Override
    public EstudianteGrupo crearOeditar(EstudianteGrupo estudianteGrupo) {
        return estudianteGrupoRepository.save(estudianteGrupo);
    }

    @Override
    public void eliminarPorId(Integer id) {
        estudianteGrupoRepository.deleteById(id);
    }
}
