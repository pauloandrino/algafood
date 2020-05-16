package com.algaworks.algafoodapi.domain.repository;

import com.algaworks.algafoodapi.domain.model.FormaPagamento;

import java.util.List;

public interface FormaPagamentoRepository {

    public List<FormaPagamento> listar();
    public FormaPagamento buscar(Long id);
    public FormaPagamento salvar(FormaPagamento formaPagamento);
    public void remover(FormaPagamento formaPagamento);
}
