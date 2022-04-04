package com.algaworks.algafoodapi.api.controller;

import com.algaworks.algafoodapi.api.ResourceUriHelper;
import com.algaworks.algafoodapi.api.assembler.CidadeInputDisasembler;
import com.algaworks.algafoodapi.api.assembler.CidadeModelAssembler;
import com.algaworks.algafoodapi.api.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafoodapi.api.model.CidadeModel;
import com.algaworks.algafoodapi.api.model.input.CidadeInput;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodapi.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafoodapi.domain.exception.NegocioException;
import com.algaworks.algafoodapi.domain.model.Cidade;
import com.algaworks.algafoodapi.domain.repository.CidadeRepository;
import com.algaworks.algafoodapi.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController()
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
    CadastroCidadeService cadastroCidade;

    @Autowired
    CidadeModelAssembler cidadeModelAssembler;

    @Autowired
    private CidadeInputDisasembler cidadeInputDisasembler;

    @GetMapping
    public CollectionModel<CidadeModel> listar() {
        List<Cidade> cidades = cidadeRepository.findAll();
        return cidadeModelAssembler.toCollectionModel(cidades);
    }

    @GetMapping("{cidadeId}")
    public CidadeModel buscar(@PathVariable Long cidadeId) {
        Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);
        return cidadeModelAssembler.toModel(cidade);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeInputDisasembler.toDomainObject(cidadeInput);
            var cidadeModel = cidadeModelAssembler.toModel(cadastroCidade.salvar(cidade));

            ResourceUriHelper.addUriResponseHeader(cidadeModel.getId());

            return cidadeModel;
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{cidadeId}")
    public CidadeModel atualizar(@PathVariable Long cidadeId,
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

    @DeleteMapping("/{cidadeId}")
    public void remover(@PathVariable Long cidadeId) {
        cadastroCidade.excluir(cidadeId);
    }

}
