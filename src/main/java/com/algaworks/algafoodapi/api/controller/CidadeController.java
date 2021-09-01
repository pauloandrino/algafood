package com.algaworks.algafoodapi.api.controller;

import com.algaworks.algafoodapi.api.ExceptionHandler.Problem;
import com.algaworks.algafoodapi.api.assembler.CidadeInputDisasembler;
import com.algaworks.algafoodapi.api.assembler.CidadeModelAssembler;
import com.algaworks.algafoodapi.api.model.CidadeModel;
import com.algaworks.algafoodapi.api.model.input.CidadeInput;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodapi.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafoodapi.domain.exception.NegocioException;
import com.algaworks.algafoodapi.domain.model.Cidade;
import com.algaworks.algafoodapi.domain.repository.CidadeRepository;
import com.algaworks.algafoodapi.domain.service.CadastroCidadeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Cidades")
@RestController()
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
    CadastroCidadeService cadastroCidade;

    @Autowired
    CidadeModelAssembler cidadeModelAssembler;

    @Autowired
    private CidadeInputDisasembler cidadeInputDisasembler;

    @ApiOperation("Lista as cidades")
    @GetMapping
    public List<CidadeModel> listar() {
        List<Cidade> cidades = cidadeRepository.findAll();
        return cidadeModelAssembler.toCollectionModel(cidades);
    }

    @ApiOperation("Busca cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    @GetMapping("{cidadeId}")
    public CidadeModel buscar(@ApiParam(value = "Id de uma cidade", example = "1")
                              @PathVariable Long cidadeId) {
        Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);
        return cidadeModelAssembler.toModel(cidade);
    }

    @ApiOperation("Cadastra uma cidade")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade cadastrada")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade")
                                 @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeInputDisasembler.toDomainObject(cidadeInput);
            return cidadeModelAssembler.toModel(cadastroCidade.salvar(cidade));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @ApiOperation("Atualiza uma cidade")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade atualizada"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    @PutMapping("/{cidadeId}")
    public CidadeModel atualizar(@ApiParam(value = "ID de uma cidade", example = "1")
                                 @PathVariable Long cidadeId,
                                 @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados")
                                 @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);

            cidadeInputDisasembler.copyToDomainInObject(cidadeInput, cidadeAtual);

            cidadeAtual = cadastroCidade.salvar(cidadeAtual);

            return cidadeModelAssembler.toModel(cidadeAtual);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @ApiOperation("Exclui uma cidade")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cidade excluída"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    @DeleteMapping("/{cidadeId}")
    public void remover(@ApiParam(value = "ID de uma cidade", example = "1")
                        @PathVariable Long cidadeId) {
        cadastroCidade.excluir(cidadeId);
    }

}
