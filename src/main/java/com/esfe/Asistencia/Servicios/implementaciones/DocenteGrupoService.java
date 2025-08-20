package com.esfe.Asistencia.Servicios.implementaciones;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service; // 🔹 Importar

import com.esfe.Asistencia.Modelos.DocenteGrupo;
import com.esfe.Asistencia.Repositorios.IDocenteGrupoRepository;
import com.esfe.Asistencia.Servicios.Interfaces.IDocenteGrupoService;

@Service // 🔹 Esto hace que Spring lo registre como bean
public class DocenteGrupoService implements IDocenteGrupoService {

    @Autowired
    private IDocenteGrupoRepository docentegrupoRepository;

    @Override
    public Page<DocenteGrupo> buscarTodosPaginados(Pageable pageable) {
        return docentegrupoRepository.findByOrderByDocenteDesc(pageable);
    }

    @Override
    public List<DocenteGrupo> obtenerTodos() {
        return docentegrupoRepository.findAll();
    }

    @Override
    public DocenteGrupo buscarPorId(Integer id) {
        return docentegrupoRepository.findById(id).orElse(null); // 🔹 mejor usar orElse(null) o lanzar excepción
    }

    @Override
    public DocenteGrupo crearOeditar(DocenteGrupo docentegrupo) {
        return docentegrupoRepository.save(docentegrupo);
    }

    @Override
    public void eliminarPorId(Integer id) {
        docentegrupoRepository.deleteById(id);
    }
}
