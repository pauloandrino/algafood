package com.algaworks.algafoodapi.domain.repository;

import com.algaworks.algafoodapi.domain.model.Permissao;

import java.util.List;

public interface PermissaoRepository {

    public List<Permissao> listar();
    public Permissao buscar(Long id);
    public Permissao salvar(Permissao permissao);
    public void remover(Permissao permissao);
}
