package com.algaworks.algafoodapi;

import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.service.CadastroCozinhaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.junit.Assert.assertFalse;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroCozinhaIntegrationTest {

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Test
	public void testarCadastroCozinhaComSucesso() {

		// cenário
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");

		// ação
		novaCozinha = cadastroCozinha.salvar(novaCozinha);

		// validação
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();

	}

	@Test(expected = ConstraintViolationException.class)
	public void testarCadastroCozinhaSemNome() {

		// cenário
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);

		// ação
		novaCozinha = cadastroCozinha.salvar(novaCozinha);
	}

}
