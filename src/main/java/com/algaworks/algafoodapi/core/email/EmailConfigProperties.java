package com.algaworks.algafoodapi.core.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("aws.email")
public class EmailConfigProperties {

    public String awsAccessKey;
    public String awsSecretKey;
    public String awsEndPoint;

}
