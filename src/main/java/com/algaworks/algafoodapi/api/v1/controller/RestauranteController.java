package com.algaworks.algafoodapi.api.v1.controller;

import com.algaworks.algafoodapi.api.v1.assembler.RestauranteApenasNomeModelAssembler;
import com.algaworks.algafoodapi.api.v1.assembler.RestauranteBasicoModelAssembler;
import com.algaworks.algafoodapi.api.v1.assembler.RestauranteInputDisasembler;
import com.algaworks.algafoodapi.api.v1.assembler.RestauranteModelAssembler;
import com.algaworks.algafoodapi.api.v1.openapi.controller.RestauranteControllerOpenApi;
import com.algaworks.algafoodapi.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafoodapi.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafoodapi.api.v1.model.RestauranteModel;
import com.algaworks.algafoodapi.api.v1.model.input.RestauranteInput;
import com.algaworks.algafoodapi.core.security.CheckSecurity;
import com.algaworks.algafoodapi.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafoodapi.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafoodapi.domain.exception.NegocioException;
import com.algaworks.algafoodapi.domain.exception.RestauranteNaoEncontradaException;
import com.algaworks.algafoodapi.domain.exception.ValidationException;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.repository.RestauranteRepository;
import com.algaworks.algafoodapi.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi {

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private SmartValidator validator;

    @Autowired
    RestauranteModelAssembler restauranteModelAssembler;

    @Autowired
    RestauranteInputDisasembler restauranteInputDisasembler;

    @Autowired
    private RestauranteBasicoModelAssembler restauranteBasicoModelAssembler;

    @Autowired
    private RestauranteApenasNomeModelAssembler restauranteApenasNomeModelAssembler;
/*

    @GetMapping
    public MappingJacksonValue listar(@RequestParam(required = false) String projecao) {
        List<Restaurante> restaurantes = restauranteRepository.findAll();
        List<RestauranteModel> restaurantesModel = restauranteModelAssembler.toCollectionModel(restaurantes);

        MappingJacksonValue restaurantesWrapper = new MappingJacksonValue(restaurantesModel);

        restaurantesWrapper.setSerializationView(RestauranteView.Resumo.class);

        if ("apenas-nome".equals(projecao)) {
            restaurantesWrapper.setSerializationView(RestauranteView.ApenasNome.class);
        } else if ("completo".equals(projecao)) {
            restaurantesWrapper.setSerializationView(null);
        }

        return restaurantesWrapper;
    }
*/

    @CheckSecurity.Restaurantes.PodeConsultar
    @Override
    @GetMapping
    public CollectionModel<RestauranteBasicoModel> listar() {
        return restauranteBasicoModelAssembler
                .toCollectionModel(restauranteRepository.findAll());
    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @Override
    @GetMapping(params = "projecao=apenas-nome")
    public CollectionModel<RestauranteApenasNomeModel> listarApenasNomes() {
        return restauranteApenasNomeModelAssembler
                .toCollectionModel(restauranteRepository.findAll());
    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping("{restauranteId}")
    public RestauranteModel buscar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        RestauranteModel restauranteModel = restauranteModelAssembler.toModel(restaurante);

        return restauranteModel;

    }

    @CheckSecurity.Restaurantes.PodeEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModel adicionar(
            @RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restaurante = restauranteInputDisasembler.toDomainModel(restauranteInput);
            return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @CheckSecurity.Restaurantes.PodeEditar
    @PutMapping("/{restauranteId}")
    public RestauranteModel atualizar(@PathVariable Long restauranteId, @RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);

            restauranteInputDisasembler.copyToDomainInObject(restauranteInput, restauranteAtual);

            return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    private void validate(Restaurante restaurante, String objectName) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
        validator.validate(restaurante, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
    }

    private void merge(@RequestBody Map<String, Object> dadosOrigem, Restaurante restauranteDestino,
                       HttpServletRequest request) {

        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

            dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
                Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
                field.setAccessible(true);

                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

                ReflectionUtils.setField(field, restauranteDestino, novoValor);
            });
        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);

        }

    }

    @CheckSecurity.Restaurantes.PodeEditar
    @Override
    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
        cadastroRestaurante.ativar(restauranteId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeEditar
    @Override
    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar(@PathVariable Long restauranteId) {
        cadastroRestaurante.inativar(restauranteId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeEditar
    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> restauranteIds) {
        try {
            cadastroRestaurante.ativar(restauranteIds);
        } catch (RestauranteNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @CheckSecurity.Restaurantes.PodeEditar
    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiplos(@RequestBody List<Long> restauranteIds) {
        try {
            cadastroRestaurante.inativar(restauranteIds);
        } catch (RestauranteNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    @CheckSecurity.Restaurantes.PodeEditar
    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> abrir(@PathVariable Long restauranteId) {
        cadastroRestaurante.abrir(restauranteId);

        return ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.Restaurantes.PodeEditar
    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> fechar(@PathVariable Long restauranteId) {
        cadastroRestaurante.fechar(restauranteId);

        return ResponseEntity.noContent().build();
    }
}
