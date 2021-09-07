package com.algaworks.algafoodapi.domain.listeners;

import com.algaworks.algafoodapi.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafoodapi.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class LogarClientePedidoCanceladoListener {

    @Autowired
    private EnvioEmailService envioEmail;

    @TransactionalEventListener(/*phase = TransactionPhase.BEFORE_COMMIT*/)
                                // Default - After commit, it's OK if the email fails
                                // Use Before COMMIT if the listener MUST be success
    public void aoCancelarPedido(PedidoCanceladoEvent event) {

        System.out.println("Listener");
        var pedido = event.getPedido();

        System.out.println("Pedido cancelado " + pedido.getCodigo());
    }
}
