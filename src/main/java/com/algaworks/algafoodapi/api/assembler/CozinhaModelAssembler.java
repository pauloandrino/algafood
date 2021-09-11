package com.algaworks.algafoodapi.api.assembler;

import com.algaworks.algafoodapi.api.controller.CozinhaController;
import com.algaworks.algafoodapi.api.model.CozinhaModel;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CozinhaModelAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModel> {

    @Autowired
    ModelMapper modelMapper;

    public CozinhaModelAssembler() {
        super(CozinhaController.class, CozinhaModel.class);
    }

    public CozinhaModel toModel(Cozinha cozinha) {
        var cozinhaModel = createModelWithId(cozinha.getId(), cozinha);

        modelMapper.map(cozinha, cozinhaModel);

        cozinhaModel.add(linkTo(CozinhaController.class).withRel("cozinhas"));

        return cozinhaModel;
    }

}
