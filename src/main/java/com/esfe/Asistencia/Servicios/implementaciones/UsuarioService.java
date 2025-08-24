package com.esfe.Asistencia.Servicios.implementaciones;


import com.esfe.Asistencia.Repositorios.IUsuarioRepository;
import com.esfe.Asistencia.Servicios.Interfaces.IUsuarioService;
import com.esfe.Asistencia.Modelos.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import java.util.List;

@Service
public class UsuarioService implements IUsuarioService {
    @Autowired  
    private IUsuarioRepository usuarioRepository;

    @Override
    public Page<Usuario> obtenerTodosPaginados(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }
    @Override
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }
    @Override
    public Usuario obtenerPorId(Integer id) {
        return usuarioRepository.findById(id).get();
    }
    @Override
    public Usuario crearOEditar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    @Override
    public void eliminar(Integer id) {
        usuarioRepository.deleteById(id);
    }

}
