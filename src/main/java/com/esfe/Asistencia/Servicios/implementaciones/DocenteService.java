package com.esfe.Asistencia.Servicios.implementaciones;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.esfe.Asistencia.Modelos.Docente;
import com.esfe.Asistencia.Repositorios.IDocenteRepository;
import com.esfe.Asistencia.Servicios.Interfaces.IDocenteService;

@Service
public class DocenteService implements IDocenteService{
    @Autowired
    private IDocenteRepository docenteRepository;
    
    @Override
    public Page<Docente> buscarTodos(Pageable pageable){
        return docenteRepository.findAll(pageable);
    }

    @Override
    public List<Docente> obtenerTodos(){
       return docenteRepository.findAll();
    }

    @Override
    public Optional<Docente> buscarPorId(Integer id){
        return docenteRepository.findById(id);
    }

    @Override
    public Docente crearOeditar(Docente docente){
        return docenteRepository.save(docente);
    }

    @Override
    public void eliminarPorId(Integer id){
        docenteRepository.deleteById(id);
    }
}
