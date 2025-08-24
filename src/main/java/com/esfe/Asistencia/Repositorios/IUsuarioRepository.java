package com.esfe.Asistencia.Repositorios;

import com.esfe.Asistencia.Modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {

}
