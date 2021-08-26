package com.algaworks.algafoodapi.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//@ApiModel(value = "Cidade", description = "Representa uma cidade")    // commented - it is too obvious, it is not necessary
@Getter
@Setter
public class CidadeModel {

    //    @ApiModelProperty(value = "ID da cidade", example = "1")
    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Uberlândia")
    private String nome;
    private EstadoModel estado;
}
