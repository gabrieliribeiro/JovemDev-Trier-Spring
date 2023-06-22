package br.com.trier.spring.services;

import br.com.trier.spring.domain.Equipe;
import br.com.trier.spring.domain.Pais;
import br.com.trier.spring.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.spring.services.exceptions.ViolacaoIntegridade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.trier.spring.BaseTests;
import jakarta.transaction.Transactional;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
public class EquipeServiceTest extends BaseTests{
	
	@Autowired
	EquipeService equipeService;

	@Test
	@DisplayName("Teste busca todas as equipes")
	@Sql(scripts = "classpath:sql/equipe.sql")
	void findAllCountriesTest() {
		List<Equipe> lista = equipeService.listAll();
		assertEquals(4, lista.size());
	}

	@Test
	@DisplayName("Teste busca equipes por ID")
	@Sql(scripts = "classpath:sql/equipe.sql")
	void findByIdTest() {
		var equipe = equipeService.findById(1);
		assertNotEquals(equipe, null);
		assertEquals(1, equipe.getId());
		assertEquals("Ferrari", equipe.getNome());
	}

	@Test
	@DisplayName("Teste busca por ID inválido")
	@Sql(scripts = "classpath:sql/equipe.sql")
	void findNotValidIDTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, ()-> equipeService.findById(8));
		assertEquals("Equipe 8 não encontrada", exception.getMessage());
	}

	@Test
	@DisplayName("Teste de incluir uma equipe")
	void insertCountryTest(){
		var equipe = new Equipe(1, "Lotus");
		equipeService.salvar(equipe);
		equipe = equipeService.findById(1);
		assertEquals(1, equipe.getId());
		assertEquals("Lotus", equipe.getNome());
	}

	@Test
	@DisplayName("Teste incluir equipe com nome inválido")
	@Sql(scripts = "classpath:sql/equipe.sql")
	void insertInvalidNameTest() {
		var equipe = new Equipe(null, "Ferrari");
		var exception = assertThrows(ViolacaoIntegridade.class, () -> equipeService.salvar(equipe));
		assertEquals("Este é o nome de uma equipe já existente", exception.getMessage());
	}

	@Test
	@DisplayName("Teste de alterar uma equipe inexistente")
	@Sql(scripts = "classpath:sql/equipe.sql")
	void updateNonexistenteCountry(){
		var equipe = new Equipe(10, "Chile");
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> equipeService.findById(10));
		assertEquals("Equipe 10 não encontrada", exception.getMessage());
	}

	@Test
	@DisplayName("Teste de deletar uma equipe")
	@Sql(scripts = "classpath:sql/equipe.sql")
	void deleteCountryTest(){
		equipeService.delete(1);
		List<Equipe> lista = equipeService.listAll();
		assertEquals(3, lista.size());
		assertEquals(4, lista.get(0).getId());
	}
}
