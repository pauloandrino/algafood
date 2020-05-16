package com.algaworks.algafoodapi.domain.repository;

import com.algaworks.algafoodapi.domain.model.Estado;

import java.util.List;

public interface EstadoRepository {

    public List<Estado> listar();
    public Estado buscar(Long id);
    public Estado salvar(Estado estado);
    public void remover(Estado estado);
}
