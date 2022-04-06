package com.algaworks.algafoodapi.api.v1.assembler;

import com.algaworks.algafoodapi.api.v1.model.FormaPagamentoInput;
import com.algaworks.algafoodapi.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoInputDisasembler {

    @Autowired
    ModelMapper modelMapper;

    public FormaPagamento toDomainModel(FormaPagamentoInput formaPagamentoInput) {
        return modelMapper.map(formaPagamentoInput, FormaPagamento.class);
    }

    public void copyToDomainObject(FormaPagamentoInput formaPAgamentoInput, FormaPagamento formaPagamentoAtual) {
        modelMapper.map(formaPAgamentoInput, formaPagamentoAtual);
    }
}
