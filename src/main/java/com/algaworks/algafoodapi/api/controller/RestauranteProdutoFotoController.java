package com.algaworks.algafoodapi.api.controller;

import com.algaworks.algafoodapi.api.model.input.FotoProdutoInput;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void atualizarFoto(@PathVariable Long restauranteId,
                              @PathVariable Long produtoId,
                              @Valid FotoProdutoInput fotoProdutoInput) {

        var nomeArquivo = UUID.randomUUID().toString()
                + "_" + fotoProdutoInput.getArquivo().getOriginalFilename();

        var arquivoFoto = Path.of("/Users/Paulo/Desktop/upload", nomeArquivo);

        System.out.println(fotoProdutoInput.getDescricao());
        System.out.println(arquivoFoto);
        System.out.println(fotoProdutoInput.getArquivo().getContentType());

        try {
            fotoProdutoInput.getArquivo().transferTo(arquivoFoto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
