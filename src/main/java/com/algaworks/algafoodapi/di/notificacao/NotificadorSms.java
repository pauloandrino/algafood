package com.algaworks.algafoodapi.di.notificacao;

import com.algaworks.algafoodapi.di.model.Cliente;
import org.springframework.stereotype.Component;

@TipoNotificador(NivelUrgencia.NORMAL)
@Component
public class NotificadorSms implements Notificador {

    @Override
    public void notificar(Cliente cliente, String mensagem) {
        System.out.printf("Noticando %s por SMS através do telefone %s: %s",
                cliente.getNome(), cliente.getTelefone(), mensagem);
    }
}
