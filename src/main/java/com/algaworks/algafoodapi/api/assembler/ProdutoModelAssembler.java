package com.algaworks.algafoodapi.api.assembler;

import com.algaworks.algafoodapi.api.AlgaLinks;
import com.algaworks.algafoodapi.api.controller.RestauranteProdutoController;
import com.algaworks.algafoodapi.api.model.ProdutoModel;
import com.algaworks.algafoodapi.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoModelAssembler
        extends RepresentationModelAssemblerSupport<Produto, ProdutoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public ProdutoModelAssembler() {
        super(RestauranteProdutoController.class, ProdutoModel.class);
    }

    @Override
    public ProdutoModel toModel(Produto produto) {
        ProdutoModel produtoModel = createModelWithId(
                produto.getId(), produto, produto.getRestaurante().getId());

        modelMapper.map(produto, produtoModel);

        produtoModel.add(algaLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));

        return produtoModel;
    }
}
