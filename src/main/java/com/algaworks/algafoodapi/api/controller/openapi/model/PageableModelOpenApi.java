package com.algaworks.algafoodapi.api.controller.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel("Pageable")
@Setter
@Getter
public class PageableModelOpenApi {

    @ApiModelProperty(example = "0", value = "Número da página (começa com 0)")
    private int page;

    @ApiModelProperty(example = "0", value = "Número de elementos por página (começa com 0)")
    private int size;

    @ApiModelProperty(example = "nome,asc", value = "Nome da propriedade para ordenação")
    private List<String> sort;
}
