package com.algaworks.algafoodapi.domain.listeners;

import com.algaworks.algafoodapi.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafoodapi.domain.service.EnvioEmailService;
import com.algaworks.algafoodapi.domain.service.EnvioEmailService.Mensagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoClientePedidoConfirmadoListener {

    @Autowired
    private EnvioEmailService envioEmail;

    @TransactionalEventListener(/*phase = TransactionPhase.BEFORE_COMMIT*/) // Default - After commit, it's OK if the email fails
                                                                            // Use Before COMMIT if the listener MUST be success
    public void aoConfirmarPedido(PedidoConfirmadoEvent event) {

        System.out.println("Listener");
        var pedido = event.getPedido();

        var mensagem = Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome())
                .corpo("emails/pedido-confirmado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmail.enviar(mensagem);
    }
}
