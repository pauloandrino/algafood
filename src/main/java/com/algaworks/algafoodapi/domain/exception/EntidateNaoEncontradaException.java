package com.algaworks.algafoodapi.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntidateNaoEncontradaException extends RuntimeException {


    public EntidateNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
