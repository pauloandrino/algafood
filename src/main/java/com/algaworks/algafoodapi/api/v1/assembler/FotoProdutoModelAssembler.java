package com.algaworks.algafoodapi.api.v1.assembler;

import com.algaworks.algafoodapi.api.v1.AlgaLinks;
import com.algaworks.algafoodapi.api.v1.controller.RestauranteProdutoFotoController;
import com.algaworks.algafoodapi.api.v1.model.FotoProdutoModel;
import com.algaworks.algafoodapi.core.security.AlgaSecurity;
import com.algaworks.algafoodapi.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoModelAssembler
        extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public FotoProdutoModelAssembler() {
        super(RestauranteProdutoFotoController.class, FotoProdutoModel.class);
    }

    @Override
    public FotoProdutoModel toModel(FotoProduto foto) {
        FotoProdutoModel fotoProdutoModel = modelMapper.map(foto, FotoProdutoModel.class);

        // Quem pode consultar restaurantes, também pode consultar os produtos e fotos
        if (algaSecurity.podeConsultarRestaurantes()) {
            fotoProdutoModel.add(algaLinks.linkToFotoProduto(
                    foto.getRestauranteId(), foto.getProduto().getId()));

            fotoProdutoModel.add(algaLinks.linkToProduto(
                    foto.getRestauranteId(), foto.getProduto().getId(), "produto"));
        }

        return fotoProdutoModel;
    }
}
