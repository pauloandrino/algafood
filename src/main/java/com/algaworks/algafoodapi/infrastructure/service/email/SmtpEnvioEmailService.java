package com.algaworks.algafoodapi.infrastructure.service.email;

import com.algaworks.algafoodapi.core.email.EmailProperties;
import com.algaworks.algafoodapi.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class SmtpEnvioEmailService implements EnvioEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailProperties emailProperties;

    @Override
    public void enviar(Mensagem mensagem) {

        try {
            var mimeMessage = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(mimeMessage, "UTF-8");

            helper.setFrom(emailProperties.getRemetente());
            helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
            helper.setSubject(mensagem.getAssunto());
            helper.setText(mensagem.getCorpo(), true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new EmailException("Não foi possível enviar email", e);
        }
    }
}
