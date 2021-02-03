package com.algaworks.algafoodapi.domain.service;

import com.algaworks.algafoodapi.domain.model.Pedido;
import com.algaworks.algafoodapi.domain.service.EnvioEmailService.Mensagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FluxoPedidoService {

    @Autowired
    EmissaoPedidoService emissaoPedido;

    @Autowired
    EnvioEmailService envioEmail;

    @Transactional
    public void confirmar(String codigoPedido) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
        pedido.confirmar();

        var mensagem = Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome())
                .corpo("O pedido de código <string>"
                        + pedido.getCodigo() + "<strogn> foi confirmado")
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmail.enviar(mensagem);
    }


    @Transactional
    public void cancelar(String codigoPedido) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
        pedido.cancelar();
    }

    @Transactional
    public void entregar(String codigoPedido) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
        pedido.entregar();
    }


}
