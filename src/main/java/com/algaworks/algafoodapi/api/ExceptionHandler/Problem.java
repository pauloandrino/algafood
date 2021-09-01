
package com.algaworks.algafoodapi.api.ExceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@ApiModel("Problema")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class Problem {

    @ApiModelProperty(example = "400", position = 1)
    private Integer status;

    @ApiModelProperty(example = "https://algafood.com.br/recurso-nao-encontrado", position = 5)
    private String type;

    @ApiModelProperty(example = "Dados inválidos", position = 10)
    private String title;

    @ApiModelProperty(example = "Não existe um cadastro de restaurante com código 12", position = 15)
    private String detail;

    @ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto", position = 20)
    private String userMessage;

    @ApiModelProperty(example = "2021-08-27T13:02:02.6707454Z", position = 25)
    private OffsetDateTime timestamp;

    @ApiModelProperty(value = "Lista de objetos ou campos que geraram o erro (opcional)", position = 30)
    private List<Object> objects;

    @ApiModel("ObjetoProblema")
    @Getter
    @Builder
    public static class Object {

        @ApiModelProperty(example = "preço")
        private String name;

        @ApiModelProperty(example = "O preço é obrigatório")
        private String userMessage;
    }

}
