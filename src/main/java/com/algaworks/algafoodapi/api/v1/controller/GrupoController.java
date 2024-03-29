package com.algaworks.algafoodapi.api.v1.controller;

import com.algaworks.algafoodapi.api.v1.assembler.GrupoInputDisasembler;
import com.algaworks.algafoodapi.api.v1.assembler.GrupoModelAssembler;
import com.algaworks.algafoodapi.api.v1.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.algafoodapi.api.v1.model.GrupoModel;
import com.algaworks.algafoodapi.api.v1.model.input.GrupoInput;
import com.algaworks.algafoodapi.core.security.CheckSecurity;
import com.algaworks.algafoodapi.domain.model.Grupo;
import com.algaworks.algafoodapi.domain.repository.GrupoRepository;
import com.algaworks.algafoodapi.domain.service.CadastroGrupoService;
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

@RestController
@RequestMapping(path = "/v1/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private GrupoModelAssembler grupoAssembler;

    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @Autowired
    private GrupoInputDisasembler grupoInputDisasembler;

    @Override
    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping
    public CollectionModel<GrupoModel> listar() {
        List<Grupo> todosGrupos = grupoRepository.findAll();

        return grupoAssembler.toCollectionModel(todosGrupos);
    }

    @GetMapping("/{grupoId}")
    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    public GrupoModel buscar(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
        return grupoAssembler.toModel(grupo);
    }

    @PostMapping
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoInput) {

        Grupo grupo = grupoInputDisasembler.toDomainObject(grupoInput);

        grupo = cadastroGrupo.salvar(grupo);

        return grupoAssembler.toModel(grupo);
    }

    @PutMapping("/{grupoId}")
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    public GrupoModel atualizar(@PathVariable Long grupoId,
                                @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupoAtual = cadastroGrupo.buscarOuFalhar(grupoId);

        grupoInputDisasembler.copyToDomainObject(grupoInput, grupoAtual);

        grupoAtual = cadastroGrupo.salvar(grupoAtual);

        return grupoAssembler.toModel(grupoAtual);
    }

    @DeleteMapping("/{grupoId}")
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    public void remover(@PathVariable Long grupoId) {
        cadastroGrupo.excluir(grupoId);
    }
}
