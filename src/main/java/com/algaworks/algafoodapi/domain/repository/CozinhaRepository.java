package com.algaworks.algafoodapi.domain.repository;

import com.algaworks.algafoodapi.domain.model.Cozinha;

import java.util.List;

public interface CozinhaRepository {

    public List<Cozinha> listar();
    public List<Cozinha> consultarPorNome(String nome);
    public Cozinha buscar(Long id);
    public Cozinha salvar(Cozinha cozinha);
    public void remover(Long id);
}
