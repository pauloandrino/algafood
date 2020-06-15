package com.algaworks.algafoodapi.api.model;

import com.algaworks.algafoodapi.domain.model.Estado;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeModel {

    private Long id;
    private String nome;
    private Estado estado;
}
