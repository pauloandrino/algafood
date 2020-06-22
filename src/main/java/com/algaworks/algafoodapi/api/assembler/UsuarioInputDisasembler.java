package com.algaworks.algafoodapi.api.assembler;

import com.algaworks.algafoodapi.api.model.input.UsuarioComSenhaInput;
import com.algaworks.algafoodapi.api.model.input.UsuarioInput;
import com.algaworks.algafoodapi.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioInputDisasembler {

    @Autowired
    ModelMapper modelMapper;

    public Usuario toDomainObject(UsuarioInput usuarioInput) {
        return modelMapper.map(usuarioInput, Usuario.class);
    }

    public void copyToDomainObject(UsuarioInput usuarioInput, Usuario usuarioAtual) {
        modelMapper.map(usuarioInput, usuarioAtual);
    }

}
