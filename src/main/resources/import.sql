-- This file populates database at application start

insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');

insert into restaurante (id, nome, taxa_frete, cozinha_id) values (1, 'Thai Gourmet', 10.90, 1);
insert into restaurante (id, nome, taxa_frete, cozinha_id) values (2, 'Thai Delivery', 9.90, 1);
insert into restaurante (id, nome, taxa_frete, cozinha_id) values (3, 'Tuk Tuk Indiana', 2.90, 2);