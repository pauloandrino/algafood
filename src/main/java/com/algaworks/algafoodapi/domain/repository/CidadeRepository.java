package com.algaworks.algafoodapi.domain.repository;

import com.algaworks.algafoodapi.domain.model.Cidade;

import java.util.List;

public interface CidadeRepository {

    public List<Cidade> listar();
    public Cidade buscar(Long id);
    public Cidade salvar(Cidade cidade);
    public void remover(Long cidadeId);
}
