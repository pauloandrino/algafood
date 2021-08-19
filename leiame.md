# Leiame

Projeto de estudo do curso Especialista Rest da Algaworks.



###For developement only:

#####Run mySql in docker:

Run docker compose

(@Deprecated) docker run --name algafood-sql -e MYSQL_ROOT_PASSWORD=123456 -e MYSQL_DATABASE=algafooddb -e TZ=America/Sao_Paulo -p 3306:3306 -d mysql:latest



#####Run localstack in docker:
######AWS CLI
#######S3 config
```
aws --endpoint-url=http://localhost:4566 s3 mb s3://algafood-test
aws --endpoint-url=http://localhost:4566 s3api put-bucket-acl --bucket algafood-test --acl public-read
```

-- Listar
```
aws --endpoint-url=http://localhost:4566  s3 ls s3://algafood-test/catalogo/
```

-- Remover all
```
aws --endpoint-url=http://localhost:4566  s3 rm s3://algafood-test/catalogo/ --recursive
```

-- Show in Browser
```
http://localhost:4566/algafood-test/catalogo/f075c5ff-d367-4284-bf95-99eac71e1a76_teste_rib.jpeg
```


-- ADD verify email
```
aws --endpoint-url=http://localhost:4566 ses verify-email-identity --email-address naoresponder@algafood.com.br
```


-- Send email
```
aws --endpoint-url=http://localhost:4566 ses send-email --from sender@example.com --destination file://destination.json --message file://message.json
```
