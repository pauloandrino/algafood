package com.algaworks.algafoodapi.api.v1.controller;

import com.algaworks.algafoodapi.api.v1.AlgaLinks;
import com.algaworks.algafoodapi.api.v1.assembler.UsuarioModelAssembler;
import com.algaworks.algafoodapi.api.v1.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.algaworks.algafoodapi.api.v1.model.UsuarioModel;
import com.algaworks.algafoodapi.core.security.AlgaSecurity;
import com.algaworks.algafoodapi.core.security.CheckSecurity;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/v1/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @Override
    @GetMapping
    public CollectionModel<UsuarioModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        CollectionModel<UsuarioModel> usuariosModel = usuarioModelAssembler
                .toCollectionModel(restaurante.getResponsaveis())
                .removeLinks();

        usuariosModel.add(algaLinks.linkToRestauranteResponsaveis(restauranteId));

        if (algaSecurity.podeGerenciarCadastroRestaurantes()) {
            usuariosModel.add(algaLinks.linkToRestauranteResponsavelAssociacao(restauranteId, "associar"));

            usuariosModel.getContent().stream().forEach(usuarioModel -> {
                usuarioModel.add(algaLinks.linkToRestauranteResponsavelDesassociacao(
                        restauranteId, usuarioModel.getId(), "desassociar"));
            });
        }

        return usuariosModel;
    }

    @Override
    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestaurante.desassociarResponsavel(restauranteId, usuarioId);

        return ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestaurante.associarResponsavel(restauranteId, usuarioId);

        return ResponseEntity.noContent().build();
    }
}
