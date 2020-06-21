package com.algaworks.algafoodapi.api.model;

import com.algaworks.algafoodapi.domain.model.Permissao;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GrupoModel {

    private Long id;
    private String nome;
    private List<Permissao> permissoes;
}
