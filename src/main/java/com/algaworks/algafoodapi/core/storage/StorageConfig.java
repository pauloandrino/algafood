package com.algaworks.algafoodapi.core.storage;

import com.algaworks.algafoodapi.domain.service.FotoStorageService;
import com.algaworks.algafoodapi.infrastructure.service.storage.LocalFotoStorageService;
import com.algaworks.algafoodapi.infrastructure.service.storage.S3FotoStorageService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Autowired
    private StorageProperties storageProperties;

    @Bean
    @ConditionalOnProperty(name = "algafood.storage.tipo", havingValue = "S3")
    public AmazonS3 amazonS3() {
        var credentials = new BasicAWSCredentials(
                storageProperties.getS3().getIdChaveAcesso(),
                storageProperties.getS3().getChaveAcessoSecreta());

        var awsEndPoint = new AwsClientBuilder.EndpointConfiguration(
                storageProperties.getS3().getEndPointUrl(),
                storageProperties.getS3().getRegion().getName());

        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(awsEndPoint)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withPathStyleAccessEnabled(true)
                .build();
    }

    @Bean
    public FotoStorageService fotoStorageService() {
        if(StorageProperties.TipoStorage.S3.equals(storageProperties.getTipo())) {
            return new S3FotoStorageService();
        } else {
            return new LocalFotoStorageService();
        }
    }
}
