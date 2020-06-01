package com.algaworks.algafoodapi.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EntidateEmUsoException extends NegocioException {

    public EntidateEmUsoException(String message) {
        super(message);
    }
}
