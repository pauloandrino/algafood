package com.algaworks.algafoodapi.api.controller;

import com.algaworks.algafoodapi.api.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafoodapi.api.assembler.FormaPagamentoInputDisasembler;
import com.algaworks.algafoodapi.api.model.FormaPagamentoInput;
import com.algaworks.algafoodapi.api.model.FormaPagamentoModel;
import com.algaworks.algafoodapi.domain.model.FormaPagamento;
import com.algaworks.algafoodapi.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafoodapi.domain.service.CadastroFormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/formasPagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController {

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamento;

    @Autowired
    private FormaPagamentoInputDisasembler formaPagamentoInputDisasembler;

    @GetMapping
    public ResponseEntity<List<FormaPagamentoModel>> listar() {
        List<FormaPagamentoModel> formaPagamentoModels = formaPagamentoModelAssembler.toCollectionModel(formaPagamentoRepository.findAll());
        return ResponseEntity.ok()
//                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
//                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
//                .cacheControl(CacheControl.noCache())
//                .cacheControl(CacheControl.noStore())
                .body(formaPagamentoModels);
    }

    @GetMapping("{formaPagamentoId}")
    public ResponseEntity<FormaPagamentoModel> buscar(@PathVariable Long formaPagamentoId) {
        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);

        FormaPagamentoModel formaPagamentoModel = formaPagamentoModelAssembler.toModel(formaPagamento);

        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
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
