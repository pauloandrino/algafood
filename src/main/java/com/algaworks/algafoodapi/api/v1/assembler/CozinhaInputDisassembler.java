package com.algaworks.algafoodapi.api.v1.assembler;

import com.algaworks.algafoodapi.api.v1.model.input.CozinhaInput;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CozinhaInputDisassembler {

    @Autowired
    ModelMapper modelMapper;

    public Cozinha toDomainObject(CozinhaInput cozinhaInput) {
        return modelMapper.map(cozinhaInput, Cozinha.class);
    }

    public void copyToDomainObject(CozinhaInput cozinhaInput, Cozinha cozinhaAtual) {
        modelMapper.map(cozinhaInput, cozinhaAtual);
    }
}
