package com.algaworks.algafoodapi.core.email;

import com.algaworks.algafoodapi.domain.service.EnvioEmailService;
import com.algaworks.algafoodapi.infrastructure.service.email.FakeEnvioEmailService;
import com.algaworks.algafoodapi.infrastructure.service.email.SandboxEnvioEmailService;
import com.algaworks.algafoodapi.infrastructure.service.email.SmtpEnvioEmailService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.mail.simplemail.SimpleEmailServiceJavaMailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class EmailConfig {

    @Autowired
    EmailConfigProperties emailConfigProperties;

    @Autowired
    EmailProperties emailProperties;

    @Bean
    public EnvioEmailService envioEmailService() {
        switch (emailProperties.getImpl()) {
            case FAKE:
                return new FakeEnvioEmailService();
            case SMTP:
                return new SmtpEnvioEmailService();
            case SANDBOX:
                return new SandboxEnvioEmailService();
            default:
                return null;
        }
    }

    @Bean
    public AmazonSimpleEmailService amazonSimpleEmailService() {

        return AmazonSimpleEmailServiceClientBuilder.standard()
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials(
                                        emailConfigProperties.getAwsAccessKey(),
                                        emailConfigProperties.getAwsAccessKey())))
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(emailConfigProperties.getAwsEndPoint(), Regions.US_EAST_1.getName())
                )
                .build();
    }

    @Bean
    public JavaMailSender javaMailSender(AmazonSimpleEmailService amazonSimpleEmailService) {
        return new SimpleEmailServiceJavaMailSender(amazonSimpleEmailService);
    }

}
