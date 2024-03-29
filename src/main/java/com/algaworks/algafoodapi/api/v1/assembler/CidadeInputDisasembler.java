package com.algaworks.algafoodapi.api.v1.assembler;

import com.algaworks.algafoodapi.api.v1.model.input.CidadeInput;
import com.algaworks.algafoodapi.domain.model.Cidade;
import com.algaworks.algafoodapi.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeInputDisasembler {

    @Autowired
    ModelMapper modelMapper;

    public Cidade toDomainObject(CidadeInput cidadeInput) {
        return modelMapper.map(cidadeInput, Cidade.class);
    }

    public void copyToDomainInObject(CidadeInput cidadeInput, Cidade cidade) {

        // to avoid to jpa tries to update Estado instead to reference to new one
        cidade.setEstado(new Estado());

        modelMapper.map(cidadeInput, cidade);
    }
}
