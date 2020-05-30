-- This file populates database at application start

insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');

insert into restaurante (id, nome, taxa_frete, cozinha_id) values (1, 'Thai Gourmet', 10.90, 1);
insert into restaurante (id, nome, taxa_frete, cozinha_id) values (2, 'Thai Delivery', 9.90, 1);
insert into restaurante (id, nome, taxa_frete, cozinha_id) values (3, 'Tuk Tuk Indiana', 2.90, 2);

insert into estado (id, nome) values (1, "São Paulo");
insert into estado (id, nome) values (2, "Minas Gerais");

insert into cidade (id, nome, estado_id) values (1, "Sorocaba", 1);
insert into cidade (id, nome, estado_id) values (2, "São Paulo", 1);
insert into cidade (id, nome, estado_id) values (3, "Belo Horizone", 2);
insert into cidade (id, nome, estado_id) values (4, "Monte Verde", 2);

insert into forma_pagamento (id, descricao) values (1, 'Cartão de crédito');
insert into forma_pagamento (id, descricao) values (2, 'Cartão de débito');
insert into forma_pagamento (id, descricao) values (3, 'Dinheiro');

insert into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3);