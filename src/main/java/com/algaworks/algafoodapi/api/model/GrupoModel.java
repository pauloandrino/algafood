package com.algaworks.algafoodapi.api.model;

import com.algaworks.algafoodapi.domain.model.Permissao;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GrupoModel {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Gerente")
    private String nome;
    private List<Permissao> permissoes;
}
