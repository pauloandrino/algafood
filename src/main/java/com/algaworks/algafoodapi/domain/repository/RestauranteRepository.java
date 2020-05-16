package com.algaworks.algafoodapi.domain.repository;

import com.algaworks.algafoodapi.domain.model.Restaurante;

import java.util.List;

public interface RestauranteRepository {

    public List<Restaurante> listar();
    public Restaurante buscar(Long id);
    public Restaurante salvar(Restaurante restaurante);
    public void remover(Restaurante restaurante);
}
