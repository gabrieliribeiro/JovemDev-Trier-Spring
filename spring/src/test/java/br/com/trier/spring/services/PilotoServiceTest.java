package br.com.trier.spring.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring.BaseTests;
import br.com.trier.spring.services.exceptions.ObjetoNaoEncontrado;
import jakarta.transaction.Transactional;

@Transactional
public class PilotoServiceTest extends BaseTests{
	
	@Autowired
	PilotoService pilotoService;
	
	@Test
	@DisplayName("Teste busca por ID")
	@Sql(scripts = "classpath:sql/equipe.sql")
	@Sql(scripts = "classpath:sql/pais.sql")
	@Sql(scripts = "classpath:sql/piloto.sql")
	void findByIdTest() {
		var piloto = pilotoService.findById(1);
		assertNotEquals(piloto, null);
		assertEquals(1, piloto.getId());
	}
	
	@Test
	@DisplayName("Teste busca por ID inválido")
	@Sql(scripts = "classpath:sql/equipe.sql")
	@Sql(scripts = "classpath:sql/pais.sql")
	@Sql(scripts = "classpath:sql/piloto.sql")
	void findNotValidIdTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class,	() -> pilotoService.findById(10));
		assertEquals("Piloto com id 10 não existe", exception.getMessage());
	}
}
