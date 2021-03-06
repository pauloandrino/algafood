package com.algaworks.algafoodapi.api.controller;

import com.algaworks.algafoodapi.api.assembler.UsuarioInputDisasembler;
import com.algaworks.algafoodapi.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafoodapi.api.model.input.SenhaInput;
import com.algaworks.algafoodapi.api.model.input.UsuarioInput;
import com.algaworks.algafoodapi.api.model.UsuarioModel;
import com.algaworks.algafoodapi.api.model.input.UsuarioComSenhaInput;
import com.algaworks.algafoodapi.domain.model.Usuario;
import com.algaworks.algafoodapi.domain.repository.UsuarioRepository;
import com.algaworks.algafoodapi.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private UsuarioInputDisasembler usuarioInputDisasembler;

    @GetMapping
    public List<UsuarioModel> listar() {
        return usuarioModelAssembler.toCollectionModel(usuarioRepository.findAll());
    }

    @GetMapping("/{usuarioId}")
    public UsuarioModel buscar(@PathVariable Long usuarioId) {
        return usuarioModelAssembler.toModel(cadastroUsuario.buscarOuFalhar(usuarioId));
    }

    @PostMapping
    public UsuarioModel adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
        Usuario usuario = usuarioInputDisasembler.toDomainObject(usuarioInput);

        return usuarioModelAssembler.toModel(cadastroUsuario.salvar(usuario));
    }

    @PutMapping("/{usuarioId}")
    public UsuarioModel atualizar(@PathVariable Long usuarioId,
                                  @RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuarioAtual = cadastroUsuario.buscarOuFalhar(usuarioId);

        usuarioInputDisasembler.copyToDomainObject(usuarioInput, usuarioAtual);

        usuarioAtual = cadastroUsuario.salvar(usuarioAtual);

        return usuarioModelAssembler.toModel(usuarioAtual);
    }

    @DeleteMapping("/{usuarioId}")
    public void remover(@PathVariable Long usuarioId) {
        cadastroUsuario.excluir(usuarioId);
    }

    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha) {
        cadastroUsuario.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
    }
}
