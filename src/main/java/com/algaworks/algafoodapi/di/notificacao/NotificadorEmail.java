package com.algaworks.algafoodapi.di.notificacao;

import com.algaworks.algafoodapi.di.model.Cliente;
import org.springframework.stereotype.Component;

@TipoNotificador(NivelUrgencia.URGENTE)
@Component
public class NotificadorEmail implements Notificador {


    private NotificadorProperties properties;

    /*
    @Value("${notificador.email.host-servidor}")
    private String host;

    @Value("${notificador.email.porta-servidor}")
    private Integer porta;
*/
    @Override
    public void notificar(Cliente cliente, String mensagem) {
        System.out.println("Host: " + properties.getHostServidor());
        System.out.println("Porta: " + properties.getPortaServidor());

        System.out.printf("Noticando %s através do email %s: %s",
                cliente.getNome(), cliente.getEmail(), mensagem);
    }
}
