package com.algaworks.algafoodapi.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntidateNaoEncontradaException extends ResponseStatusException {

    public EntidateNaoEncontradaException(HttpStatus status, String mensagem) {
        super(status, mensagem);
    }

    public EntidateNaoEncontradaException(String mensagem) {
        this(HttpStatus.NOT_FOUND, mensagem);
    }
}
