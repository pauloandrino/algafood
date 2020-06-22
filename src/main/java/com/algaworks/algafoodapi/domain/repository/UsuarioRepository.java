package com.algaworks.algafoodapi.domain.repository;

import com.algaworks.algafoodapi.domain.model.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {
}
