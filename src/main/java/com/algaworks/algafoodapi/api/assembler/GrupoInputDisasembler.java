package com.algaworks.algafoodapi.api.assembler;

import com.algaworks.algafoodapi.api.model.input.GrupoInput;
import com.algaworks.algafoodapi.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrupoInputDisasembler {

    @Autowired
    ModelMapper modelMapper;

    public Grupo toDomainObject(GrupoInput grupoInput) {
        return modelMapper.map(grupoInput, Grupo.class);
    }

    public void copyToDomainObject(GrupoInput grupoInput, Grupo grupoAtual) {
        modelMapper.map(grupoInput, grupoAtual);
    }
}
