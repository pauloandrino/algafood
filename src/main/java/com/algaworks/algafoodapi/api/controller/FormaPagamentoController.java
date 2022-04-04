package com.algaworks.algafoodapi.api.controller;

import com.algaworks.algafoodapi.api.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafoodapi.api.assembler.FormaPagamentoInputDisasembler;
import com.algaworks.algafoodapi.api.openapi.controller.FormaPagamentoControllerOpenApi;
import com.algaworks.algafoodapi.api.model.FormaPagamentoInput;
import com.algaworks.algafoodapi.api.model.FormaPagamentoModel;
import com.algaworks.algafoodapi.domain.model.FormaPagamento;
import com.algaworks.algafoodapi.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafoodapi.domain.service.CadastroFormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(path = "/formasPagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamento;

    @Autowired
    private FormaPagamentoInputDisasembler formaPagamentoInputDisasembler;

    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        var dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();

        if (dataUltimaAtualizacao != null) {
            eTag = String.valueOf((dataUltimaAtualizacao.toEpochSecond()));
        }

        if (request.checkNotModified(eTag)) {
            return null;
        }

        List<FormaPagamento> todasFormasPagamentos = formaPagamentoRepository.findAll();

        CollectionModel<FormaPagamentoModel> formasPagamentosModel =
                formaPagamentoModelAssembler.toCollectionModel(todasFormasPagamentos);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
                .eTag(eTag)
                .body(formasPagamentosModel);
    }

    @GetMapping("{formaPagamentoId}")
    public ResponseEntity<FormaPagamentoModel> buscar(@PathVariable Long formaPagamentoId, ServletWebRequest request) {

        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        OffsetDateTime dataAtualizacao = formaPagamentoRepository
                .getDataAtualizacaoById(formaPagamentoId);

        if (dataAtualizacao != null) {
            eTag = String.valueOf(dataAtualizacao.toEpochSecond());
        }

        if (request.checkNotModified(eTag)) {
            return null;
        }

        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);

        FormaPagamentoModel formaPagamentoModel = formaPagamentoModelAssembler.toModel(formaPagamento);

        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .eTag(eTag)
                .body(formaPagamentoModel);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = formaPagamentoInputDisasembler.toDomainModel(formaPagamentoInput);
        return formaPagamentoModelAssembler.toModel(cadastroFormaPagamento.salvar(formaPagamento));
    }

    @PutMapping("/{formaPagamentoId}")
    public FormaPagamentoModel atualizar(@PathVariable Long formaPagamentoId,
                                         @RequestBody @Valid FormaPagamentoInput formaPAgamentoInput) {

        FormaPagamento formaPagamentoAtual = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);

        formaPagamentoInputDisasembler.copyToDomainObject(formaPAgamentoInput, formaPagamentoAtual);

        return formaPagamentoModelAssembler.toModel(cadastroFormaPagamento.salvar(formaPagamentoAtual));
    }

    @DeleteMapping("/{formaPagamentoId}")
    public void remover(@PathVariable Long formaPagamentoId) {
        cadastroFormaPagamento.exluir(formaPagamentoId);
    }

}
