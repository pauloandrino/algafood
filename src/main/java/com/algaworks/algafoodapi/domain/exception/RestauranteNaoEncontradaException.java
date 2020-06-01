package com.algaworks.algafoodapi.domain.exception;

public class RestauranteNaoEncontradaException extends EntidateNaoEncontradaException {

    public RestauranteNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public RestauranteNaoEncontradaException(Long restauranteId) {
        this(String.format("Não existe um cadastro de restaurante com código %d", restauranteId));
    }
}
