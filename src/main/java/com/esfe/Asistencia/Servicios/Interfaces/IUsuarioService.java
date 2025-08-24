package com.esfe.Asistencia.Servicios.Interfaces;
import java.util.List;
import org.springframework.data.domain.Page;
import com.esfe.Asistencia.Modelos.Usuario;
import org.springframework.data.domain.Pageable;

public interface IUsuarioService {

    Page<Usuario> obtenerTodosPaginados(Pageable pageable);

    List<Usuario> obtenerTodos();

    Usuario obtenerPorId(Integer id);

    Usuario crearOEditar(Usuario usuario);

    void eliminar(Integer id);
}
