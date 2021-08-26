package com.algaworks.algafoodapi.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CidadeInput {

    @ApiModelProperty(example = "Uberlândia")
    @NotBlank
    private String nome;

    @Valid
    private EstadoIdInput estado;

}
